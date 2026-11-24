package com.esenciayaroma.abastecimiento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrdenCompraService {

    private final OrdenCompraRepository ordenRepository;
    private final PedidoAbastecimientoRepository pedidoRepository;

    public OrdenCompraService(
            OrdenCompraRepository ordenRepository,
            PedidoAbastecimientoRepository pedidoRepository) {

        this.ordenRepository = ordenRepository;
        this.pedidoRepository = pedidoRepository;
    }

    // Listar órdenes de compra
    public List<OrdenCompra> listarOrdenes() {
        return ordenRepository.findAll();
    }

    // Registrar una orden de compra
    @Transactional
    public OrdenCompra registrarOrden(
            OrdenCompraFormulario formulario) {

        PedidoAbastecimiento pedido = pedidoRepository
                .findById(formulario.getPedidoId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Pedido no encontrado."
                        )
                );

        if (ordenRepository.existsByPedido_Id(pedido.getId())) {
            throw new IllegalArgumentException(
                    "Este pedido ya tiene una orden de compra."
            );
        }

        if (formulario.getFechaEntrega()
                .isBefore(LocalDate.now())) {
            throw new IllegalArgumentException(
                    "La fecha de entrega no puede ser anterior a hoy."
            );
        }

        BigDecimal total = pedido.getDetalles()
                .stream()
                .map(DetallePedidoAbastecimiento::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String numero = "OC-" + System.currentTimeMillis();

        OrdenCompra orden = new OrdenCompra(
                numero,
                LocalDate.now(),
                formulario.getFechaEntrega(),
                total,
                pedido
        );

        return ordenRepository.save(orden);
    }

    // Cambiar estado de la orden
    @Transactional
    public void cambiarEstado(
            Long id,
            EstadoOrdenCompra estado) {

        OrdenCompra orden = ordenRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Orden de compra no encontrada."
                        )
                );

        orden.setEstado(estado);
        ordenRepository.save(orden);
    }
}
