package com.esenciayaroma.inventario;

import org.springframework.stereotype.Component;

@Component
public class MovimientoInventarioFactory {

    public MovimientoInventario crearMovimiento(
            Producto producto,
            TipoMovimiento tipo,
            int cantidad,
            int stockAnterior,
            int stockPosterior,
            String motivo) {

        if (producto == null) {
            throw new IllegalArgumentException(
                    "El producto es obligatorio."
            );
        }

        if (tipo == null) {
            throw new IllegalArgumentException(
                    "Debe seleccionar el tipo de movimiento."
            );
        }

        if (cantidad <= 0) {
            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor que cero."
            );
        }

        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException(
                    "Debe indicar el motivo del movimiento."
            );
        }

        if (tipo == TipoMovimiento.ENTRADA) {

            if ((long) stockAnterior + cantidad
                    != stockPosterior) {
                throw new IllegalArgumentException(
                        "Los datos de entrada no coinciden."
                );
            }

        } else if (tipo == TipoMovimiento.SALIDA) {

            if (cantidad > stockAnterior ||
                    stockAnterior - cantidad != stockPosterior) {
                throw new IllegalArgumentException(
                        "Los datos de salida no coinciden."
                );
            }

        } else {
            throw new IllegalArgumentException(
                    "Tipo de movimiento no válido."
            );
        }

        return new MovimientoInventario(
                producto,
                tipo,
                cantidad,
                stockAnterior,
                stockPosterior,
                motivo.trim()
        );
    }
}
