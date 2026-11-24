
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;

public class ResumenCobroPorMedio {

    private final String medio;
    private final long cantidad;
    private final BigDecimal total;

    public ResumenCobroPorMedio(
            String medio,
            long cantidad,
            BigDecimal total) {

        this.medio = medio;
        this.cantidad = cantidad;
        this.total = total;
    }

    public String getMedio() { return medio; }
    public long getCantidad() { return cantidad; }
    public BigDecimal getTotal() { return total; }
}
