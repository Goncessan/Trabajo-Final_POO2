
package com.esenciayaroma.ventas;

import com.esenciayaroma.inventario.*;

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
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioService movimientoService;

    public ClientesVentasService(
            ClienteRepository clienteRepository,
            PedidoClienteRepository pedidoRepository,
            VentaRepository ventaRepository,
            ProductoRepository productoRepository,
            MovimientoInventarioService movimientoService) {

        this.clienteRepository = clienteRepository;
        this.pedidoRepository = pedidoRepository;
        this.ventaRepository = ventaRepository;
        this.productoRepository = productoRepository;
        this.movimientoService = movimientoService;
    }

    // CLIENTES

    @Transactional(readOnly = true)
    public List<Cliente> listarClientes() {
        return clienteRepository.findAllByOrderByNombreAsc();
    }

    @Transactional
    public void registrarCliente(Cliente cliente) {

        cliente.setNombre(cliente.getNombre().trim());

        if (cliente.getTelefono() != null) {
            cliente.setTelefono(cliente.getTelefono().trim());
        }

        if (cliente.getDireccion() != null) {
            cliente.setDireccion(cliente.getDireccion().trim());
        }

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
                        "El cliente seleccionado no existe."
                ));

        PedidoCliente pedido = new PedidoCliente(
                cliente,
                formulario.getMarca(),
                formulario.getCodigoCatalogo().trim(),
                formulario.getNombreProducto().trim(),
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
                            "Encargo no encontrado"
                    ));

            if (pedido.getCantidadPendiente() <= 0) {
                throw new IllegalArgumentException(
                        "Este encargo ya fue entregado."
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

    @Transactional
    public void registrarVenta(VentaFormulario formulario) {

        Cliente cliente = clienteRepository
                .findById(formulario.getClienteId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El cliente seleccionado no existe."
                ));

        Producto producto = productoRepository
                .findById(formulario.getProductoId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "El producto seleccionado no existe."
                ));

        PedidoCliente pedido = null;

        // Comprobar si la venta corresponde a un encargo
        if (formulario.getPedidoClienteId() != null) {

            pedido = pedidoRepository.buscarParaEntrega(
                    formulario.getPedidoClienteId()
            ).orElseThrow(() -> new IllegalArgumentException(
                    "El encargo seleccionado no existe."
            ));

            if (!pedido.getCliente().getId()
                    .equals(cliente.getId())) {

                throw new IllegalArgumentException(
                        "El encargo pertenece a otro cliente."
                );
            }

            if (pedido.getMarca() != producto.getMarca()) {
                throw new IllegalArgumentException(
                        "El producto no pertenece a la marca del encargo."
                );
            }

            if (formulario.getCantidad()
                    > pedido.getCantidadPendiente()) {

                throw new IllegalArgumentException(
                        "La cantidad supera lo pendiente del encargo."
                );
            }
        }

        int cantidad = formulario.getCantidad();

        BigDecimal total = formulario.getPrecioUnitario()
                .multiply(BigDecimal.valueOf(cantidad));

        BigDecimal pagoInicial;

        if (formulario.getFormaPago() == FormaPago.CONTADO) {

            pagoInicial = total;

        } else {

            pagoInicial = formulario.getPagoInicial();

            if (pagoInicial.compareTo(total) > 0) {
                throw new IllegalArgumentException(
                        "El pago inicial supera el total de la venta."
                );
            }
        }

        // Descontar el producto una sola vez
        MovimientoFormulario movimiento =
                new MovimientoFormulario();

        movimiento.setProductoId(producto.getId());
        movimiento.setTipo(TipoMovimiento.SALIDA);
        movimiento.setCantidad(cantidad);
        movimiento.setMotivo("Venta a " + cliente.getNombre());

        movimientoService.registrarMovimiento(movimiento);

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
    }
}
