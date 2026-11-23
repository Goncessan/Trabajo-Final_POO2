package com.esenciayaroma.ventas;

import com.esenciayaroma.inventario.Producto;
import com.esenciayaroma.ventas.observador.ConstructorEventoVenta;
import com.esenciayaroma.ventas.observador.SujetoVentas;
import com.esenciayaroma.ventas.puerto.PuertoInventario;
import com.esenciayaroma.ventas.regla.CalculadoraPagoInicial;
import com.esenciayaroma.ventas.regla.CalculadoraTotalVenta;
import com.esenciayaroma.ventas.regla.NormalizadorCliente;
import com.esenciayaroma.ventas.regla.NormalizadorPedido;
import com.esenciayaroma.ventas.regla.ValidadorEncargoVenta;
import com.esenciayaroma.ventas.soporte.MensajesVenta;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ClientesVentasService {

    private final ClienteRepository clienteRepository;
    private final PedidoClienteRepository pedidoRepository;
    private final VentaRepository ventaRepository;
    private final PuertoInventario inventario;

    private final NormalizadorCliente normalizadorCliente;
    private final NormalizadorPedido normalizadorPedido;
    private final ValidadorEncargoVenta validadorEncargo;
    private final CalculadoraTotalVenta calculadoraTotal;
    private final CalculadoraPagoInicial calculadoraPago;

    private final SujetoVentas publicador;
    private final ConstructorEventoVenta constructorEvento;

    public ClientesVentasService(
            ClienteRepository clienteRepository,
            PedidoClienteRepository pedidoRepository,
            VentaRepository ventaRepository,
            PuertoInventario inventario,
            NormalizadorCliente normalizadorCliente,
            NormalizadorPedido normalizadorPedido,
            ValidadorEncargoVenta validadorEncargo,
            CalculadoraTotalVenta calculadoraTotal,
            CalculadoraPagoInicial calculadoraPago,
            SujetoVentas publicador,
            ConstructorEventoVenta constructorEvento) {

        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.ventaRepository = ventaRepository;
        this.inventario = inventario;
        this.normalizadorCliente = normalizadorCliente;
        this.normalizadorPedido = normalizadorPedido;
        this.validadorEncargo = validadorEncargo;
        this.calculadoraTotal = calculadoraTotal;
        this.calculadoraPago = calculadoraPago;
        this.publicador = publicador;
        this.constructorEvento = constructorEvento;
    }

    // CLIENTES

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAllByOrderByNombreAsc();
    }

    @Transactional
    public void registrarCliente(Cliente cliente) {

        normalizadorCliente.normalizar(cliente);

        clienteRepository.save(cliente);
    }

    // ENCARGOS DE CLIENTES

    @Transactional(readOnly = true)
    public List<PedidoCliente> listarPedidos() {
        return pedidoRepository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    @Transactional
    public void registrarPedido(
            PedidoClienteFormulario formulario) {

        Cliente cliente = clienteRepository
                .findById(formulario.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        MensajesVenta.CLIENTE_NO_EXISTE
                ));

        PedidoCliente pedido = new PedidoCliente(
                cliente,
                formulario.getMarca(),
                normalizadorPedido.codigoCatalogo(formulario),
                normalizadorPedido.nombreProducto(formulario),
                formulario.getCantidadSolicitada()
        );

        pedidoRepository.save(pedido);
    }

    @Transactional(readOnly = true)
    public VentaFormulario prepararFormularioVenta(
            Long pedidoId) {

        VentaFormulario formulario = new VentaFormulario();

        if (pedidoId != null) {

            PedidoCliente pedido = pedidoRepository
                    .findById(pedidoId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND,
                            MensajesVenta.ENCARGO_NO_ENCONTRADO
                    ));

            if (pedido.getCantidadPendiente() <= 0) {
                throw new IllegalArgumentException(
                        MensajesVenta.ENCARGO_YA_ENTREGADO
                );
            }

            formulario.setPedidoClienteId(pedido.getId());
            formulario.setClienteId(pedido.getCliente().getId());
            formulario.setCantidad(pedido.getCantidadPendiente());
        }

        return formulario;
    }

    // VENTAS

    @Transactional(readOnly = true)
    public List<Venta> listarVentas() {
        return ventaRepository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    /**
     * Se llama desde VentaFachada, que ya tomó el candado del producto.
     */
    @Transactional
    public void registrarVenta(VentaFormulario formulario) {

        Cliente cliente = clienteRepository
                .findById(formulario.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        MensajesVenta.CLIENTE_NO_EXISTE
                ));

        Producto producto =
                inventario.obtenerProducto(formulario.getProductoId());

        PedidoCliente pedido = null;

        // Comprobar si la venta corresponde a un encargo
        if (formulario.getPedidoClienteId() != null) {

            pedido = pedidoRepository.buscarParaEntrega(
                    formulario.getPedidoClienteId()
            ).orElseThrow(() -> new IllegalArgumentException(
                    MensajesVenta.ENCARGO_NO_EXISTE
            ));

            validadorEncargo.validar(
                    pedido,
                    cliente,
                    producto,
                    formulario.getCantidad()
            );
        }

        int cantidad = formulario.getCantidad();

        BigDecimal total = calculadoraTotal.calcular(
                formulario.getPrecioUnitario(),
                cantidad
        );

        BigDecimal pagoInicial = calculadoraPago.calcular(
                formulario.getFormaPago(),
                total,
                formulario.getPagoInicial()
        );

        // Descontar el producto una sola vez
        inventario.registrarSalida(
                producto.getId(),
                cantidad,
                cliente.getNombre()
        );

        // Actualizar las unidades entregadas del encargo
        if (pedido != null) {
            pedido.registrarEntrega(cantidad);
        }

        // Guardar la venta
        Venta venta = new Venta(
                cliente,
                producto,
                pedido,
                cantidad,
                formulario.getPrecioUnitario(),
                formulario.getFormaPago(),
                pagoInicial
        );

        ventaRepository.save(venta);

        // Avisar a los observadores (alertas de stock, bitácora...)
        publicador.notificar(
                constructorEvento.crear(venta, cliente, producto, cantidad)
        );
    }
}
