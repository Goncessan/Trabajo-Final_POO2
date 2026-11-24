
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;

public class ResumenAuditoriaCobranza {

    private final long totalOperaciones;
    private final BigDecimal totalRegistrado;
    private final long pagosDigitales;

    public ResumenAuditoriaCobranza(
            long totalOperaciones,
            BigDecimal totalRegistrado,
            long pagosDigitales) {

        this.totalOperaciones = totalOperaciones;
        this.totalRegistrado = totalRegistrado;
        this.pagosDigitales = pagosDigitales;
    }

    public long getTotalOperaciones() {
        return totalOperaciones;
    }

    public BigDecimal getTotalRegistrado() {
        return totalRegistrado;
    }

    public long getPagosDigitales() {
        return pagosDigitales;
    }
}
