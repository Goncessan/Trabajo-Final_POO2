package com.esenciayaroma.ventas.concurrencia;

/**
 * Extiende IllegalArgumentException para que el controlador la muestre
 * como cualquier otro error de la venta, sin cambios.
 */
public class VentaOcupadaException extends IllegalArgumentException {

    public VentaOcupadaException() {
        super("Otra venta de este producto está en proceso. "
                + "Inténtalo de nuevo en unos segundos.");
    }
}
