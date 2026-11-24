
package com.esenciayaroma.inventario;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovimientoInventarioService {

    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoRepository;
    private final NotificadorStock notificadorStock;
    private final MovimientoInventarioFactory movimientoFactory;
    private final AuditoriaInventarioService auditoriaService;

    public MovimientoInventarioService(
            ProductoRepository productoRepository,
            MovimientoInventarioRepository movimientoRepository,
            NotificadorStock notificadorStock,
            MovimientoInventarioFactory movimientoFactory,
            AuditoriaInventarioService auditoriaService) {

        this.productoRepository = productoRepository;
        this.movimientoRepository = movimientoRepository;
        this.notificadorStock = notificadorStock;
        this.movimientoFactory = movimientoFactory;
        this.auditoriaService = auditoriaService;
    }

    // REGISTRAR MOVIMIENTO

    @Transactional
    public void registrarMovimiento(
            MovimientoFormulario formulario) {

        // 1. Validar el formulario

        if (formulario == null ||
                formulario.getProductoId() == null ||
                formulario.getTipo() == null ||
                formulario.getCantidad() == null ||
                formulario.getCantidad() <= 0 ||
                formulario.getMotivo() == null ||
                formulario.getMotivo().isBlank()) {

            throw new IllegalArgumentException(
                    "Complete correctamente los datos del movimiento."
            );
        }

        // 2. Buscar y bloquear el producto

        Producto producto = productoRepository
                .buscarParaMovimiento(formulario.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

        int stockAnterior = producto.getStock();
        int cantidad = formulario.getCantidad();
        int stockPosterior;

        // 3. Calcular el nuevo stock

        if (formulario.getTipo() == TipoMovimiento.ENTRADA) {

            try {

                stockPosterior = Math.addExact(
                        stockAnterior,
                        cantidad
                );

            } catch (ArithmeticException e) {

                throw new IllegalArgumentException(
                        "La cantidad supera el stock máximo permitido."
                );
            }

        } else if (formulario.getTipo() == TipoMovimiento.SALIDA) {

            if (cantidad > stockAnterior) {

                throw new IllegalArgumentException(
                        "Stock insuficiente. Solo hay "
                                + stockAnterior
                                + " unidades disponibles."
                );
            }

            stockPosterior = stockAnterior - cantidad;

        } else {

            throw new IllegalArgumentException(
                    "Tipo de movimiento no válido."
            );
        }

        // 4. Crear el movimiento utilizando Factory

        MovimientoInventario movimiento =
                movimientoFactory.crearMovimiento(
                        producto,
                        formulario.getTipo(),
                        cantidad,
                        stockAnterior,
                        stockPosterior,
                        formulario.getMotivo()
                );

        // 5. Actualizar el producto

        producto.setStock(stockPosterior);
        productoRepository.save(producto);

        // 6. Guardar el movimiento

        movimientoRepository.save(movimiento);

        // 7. Registrar la operación en Auditoría

        auditoriaService.registrar(
                "MOVIMIENTO " + formulario.getTipo().name(),
                "Cantidad: " + cantidad
                        + ". Stock anterior: " + stockAnterior
                        + ". Stock posterior: " + stockPosterior
                        + ". Motivo: " + formulario.getMotivo(),
                producto.getCodigo()
        );

        // 8. Preparar el evento de Observer

        EventoCambioStock evento = new EventoCambioStock(
                producto.getId(),
                producto.getCodigo(),
                producto.getNombre(),
                stockAnterior,
                stockPosterior,
                producto.getStockMinimo()
        );

        // 9. Notificar después de confirmar la transacción

        TransactionSynchronizationManager.registerSynchronization(
                new TransactionSynchronization() {

                    @Override
                    public void afterCommit() {

                        notificadorStock.notificar(evento);
                    }
                }
        );
    }

    // CONSULTAR HISTORIAL DE MOVIMIENTOS

    @Transactional(readOnly = true)
    public List<MovimientoInventario> listarMovimientos() {

        return movimientoRepository
                .findAllByOrderByFechaDescIdDesc();
    }
}
