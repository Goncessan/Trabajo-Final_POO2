
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReporteCobranzaBuilder {

    private List<Abono> abonos;
    private BigDecimal total;
    private long cantidad;
    private LocalDate desde;
    private LocalDate hasta;

    public ReporteCobranzaBuilder conAbonos(List<Abono> abonos) {
        this.abonos = abonos;
        return this;
    }

    public ReporteCobranzaBuilder conTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public ReporteCobranzaBuilder conCantidad(long cantidad) {
        this.cantidad = cantidad;
        return this;
    }

    public ReporteCobranzaBuilder conDesde(LocalDate desde) {
        this.desde = desde;
        return this;
    }

    public ReporteCobranzaBuilder conHasta(LocalDate hasta) {
        this.hasta = hasta;
        return this;
    }

    public ReporteCobranza build() {

        if (abonos == null || total == null) {
            throw new IllegalStateException(
                    "Faltan datos para generar el reporte."
            );
        }

        return new ReporteCobranza(
                abonos,
                total,
                cantidad,
                desde,
                hasta
        );
    }
}
