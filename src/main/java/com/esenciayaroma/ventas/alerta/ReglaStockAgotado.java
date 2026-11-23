package com.esenciayaroma.ventas.alerta;

import org.springframework.stereotype.Component;

/**
 * El producto está agotado cuando no quedan unidades.
 */
@Component
public class ReglaStockAgotado {

    public boolean cumple(int stockActual) {
        return stockActual <= 0;
    }
}
