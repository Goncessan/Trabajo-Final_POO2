package com.esenciayaroma.ventas.observador;

import com.esenciayaroma.ventas.FormaPago;

/**
 * Datos de una venta recién registrada que reciben los observadores.
 * stockActual es el stock que quedó DESPUÉS de descontar la venta.
 */
public record EventoVenta(
        Long ventaId,
        Long productoId,
        String productoNombre,
        int cantidad,
        int stockActual,
        int stockMinimo,
        FormaPago formaPago,
        String clienteNombre) {
}
