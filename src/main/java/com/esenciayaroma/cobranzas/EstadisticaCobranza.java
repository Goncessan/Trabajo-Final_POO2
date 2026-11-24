
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.util.List;

public class EstadisticaCobranza {

    private final BigDecimal totalCobrado;
    private final long totalAbonos;
    private final List<ResumenCobroPorMedio> porMedio;
    private final List<ResumenCobroPorPeriodo> porPeriodo;
    private final String periodo;

    public EstadisticaCobranza(
            BigDecimal totalCobrado,
            long totalAbonos,
            List<ResumenCobroPorMedio> porMedio,
            List<ResumenCobroPorPeriodo> porPeriodo,
            String periodo) {

        this.totalCobrado = totalCobrado;
        this.totalAbonos = totalAbonos;
        this.porMedio = porMedio;
        this.porPeriodo = porPeriodo;
        this.periodo = periodo;
    }

    public BigDecimal getTotalCobrado() {
        return totalCobrado;
    }

    public long getTotalAbonos() {
        return totalAbonos;
    }

    public List<ResumenCobroPorMedio> getPorMedio() {
        return porMedio;
    }

    public List<ResumenCobroPorPeriodo> getPorPeriodo() {
        return porPeriodo;
    }

    public String getPeriodo() {
        return periodo;
    }
}
