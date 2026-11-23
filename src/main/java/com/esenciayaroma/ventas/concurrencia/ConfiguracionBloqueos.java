package com.esenciayaroma.ventas.concurrencia;

/**
 * Parámetros del control de concurrencia.
 */
public final class ConfiguracionBloqueos {

    // Segundos máximos que una venta espera a otra del mismo producto.
    public static final long ESPERA_SEGUNDOS = 10;

    private ConfiguracionBloqueos() {
    }
}
