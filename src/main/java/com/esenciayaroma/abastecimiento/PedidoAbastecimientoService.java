package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class PedidoAbastecimientoService {

    private final PedidoAbastecimientoRepository pedidoRepository;
    private final ProveedorRepository proveedorRepository;
    private final CampaniaRepository campaniaRepository;
    private final ProductoRepository productoRepository;

    public PedidoAbastecimientoService(
            PedidoAbastecimientoRepository pedidoRepository,
            ProveedorRepository proveedorRepository,
            CampaniaRepository campaniaRepository,
            ProductoRepository productoRepository) {

        this.pedidoRepository = pedidoRepository;
        this.proveedorRepository = proveedorRepository;
        this.campaniaRepository = campaniaRepository;
        this.productoRepository = productoRepository;
    }

    // Listar pedidos
    public List<PedidoAbastecimiento> listarPedidos() {
        return pedidoRepository.findAll();
    }

    // Registrar pedido
    @Transactional
    public PedidoAbastecimiento registrarPedido(
            PedidoAbastecimientoFormulario formulario) {

        Proveedor proveedor = proveedorRepository
                .findById(formulario.getProveedorId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Proveedor no encontrado."
                        )
                );

        if (!proveedor.isActivo()) {
            throw new IllegalArgumentException(
                    "El proveedor está inactivo."
            );
        }

        Campania campania = campaniaRepository
                .findById(formulario.getCampaniaId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Campaña no encontrada."
                        )
                );

        PedidoAbastecimiento pedido =
                new PedidoAbastecimiento(
                        LocalDate.now(),
                        proveedor,
                        campania,
                        formulario.getPrioridad()
                );

        Set<Long> productosRegistrados = new HashSet<>();

        for (LineaPedidoAbastecimientoFormulario linea
                : formulario.getDetalles()) {

            if (!productosRegistrados.add(linea.getProductoId())) {
                throw new IllegalArgumentException(
                        "No puedes repetir un producto en el pedido."
                );
            }

            Producto producto = productoRepository
                    .findById(linea.getProductoId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Producto no encontrado."
                            )
                    );

            if (producto.getMarca() != campania.getMarca()) {
                throw new IllegalArgumentException(
                        "El producto no pertenece a la marca de la campaña."
                );
            }

            DetallePedidoAbastecimiento detalle =
                    new DetallePedidoAbastecimiento(
                            producto,
                            linea.getCantidad(),
                            linea.getPrecioUnitario()
                    );

            pedido.agregarDetalle(detalle);
        }

        return pedidoRepository.save(pedido);
    }

    // Cambiar estado del pedido
    @Transactional
    public void cambiarEstado(
            Long id,
            EstadoPedidoAbastecimiento estado) {

        PedidoAbastecimiento pedido = pedidoRepository
                .findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Pedido no encontrado."
                        )
                );

        pedido.setEstado(estado);
        pedidoRepository.save(pedido);
    }
}
