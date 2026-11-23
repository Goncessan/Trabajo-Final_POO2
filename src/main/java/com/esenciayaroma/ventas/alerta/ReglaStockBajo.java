package com.esenciayaroma.ventas.alerta;

import org.springframework.stereotype.Component;

/**
 * Hay stock bajo cuando quedan unidades, pero no más que el mínimo.
 */
@Component
public class ReglaStockBajo {

    public boolean cumple(int stockActual, int stockMinimo) {
        return stockActual > 0 && stockActual <= stockMinimo;
    }
}
