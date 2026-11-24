
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;

public class ResumenCobroPorPeriodo {

    private final String periodo;
    private final BigDecimal total;
    private final long cantidad;

    public ResumenCobroPorPeriodo(
            String periodo,
            BigDecimal total,
            long cantidad) {

        this.periodo = periodo;
        this.total = total;
        this.cantidad = cantidad;
    }

    public String getPeriodo() { return periodo; }
    public BigDecimal getTotal() { return total; }
    public long getCantidad() { return cantidad; }
}
