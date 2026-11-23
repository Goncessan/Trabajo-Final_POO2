
package com.esenciayaroma.inventario;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MovimientoInventarioService {

    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoRepository;

    public MovimientoInventarioService(
            ProductoRepository productoRepository,
            MovimientoInventarioRepository movimientoRepository) {

        this.productoRepository = productoRepository;
        this.movimientoRepository = movimientoRepository;
    }

    @Transactional
    public void registrarMovimiento(
            MovimientoFormulario formulario) {

        // 1. Buscar y bloquear el producto durante la operación
        Producto producto = productoRepository
                .buscarParaMovimiento(formulario.getProductoId())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Producto no encontrado"
                ));

        int stockAnterior = producto.getStock();
        int cantidad = formulario.getCantidad();
        int stockPosterior;

        // 2. Calcular la nueva cantidad disponible
        if (formulario.getTipo() == TipoMovimiento.ENTRADA) {

            try {
                stockPosterior = Math.addExact(
                        stockAnterior, cantidad
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

        // 3. Actualizar el stock del producto
        producto.setStock(stockPosterior);
        productoRepository.save(producto);

        // 4. Registrar el movimiento
        MovimientoInventario movimiento =
                new MovimientoInventario(
                        producto,
                        formulario.getTipo(),
                        cantidad,
                        stockAnterior,
                        stockPosterior,
                        formulario.getMotivo().trim()
                );

        movimientoRepository.save(movimiento);
    }

    @Transactional(readOnly = true)
    public List<MovimientoInventario> listarMovimientos() {

        return movimientoRepository
                .findAllByOrderByFechaDescIdDesc();
    }
}
