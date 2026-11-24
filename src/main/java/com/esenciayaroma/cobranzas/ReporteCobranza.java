
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class ReporteCobranza {

    private final List<Abono> abonos;
    private final BigDecimal total;
    private final long cantidad;
    private final LocalDate desde;
    private final LocalDate hasta;

    public ReporteCobranza(
            List<Abono> abonos,
            BigDecimal total,
            long cantidad,
            LocalDate desde,
            LocalDate hasta) {

        this.abonos = List.copyOf(abonos);
        this.total = total;
        this.cantidad = cantidad;
        this.desde = desde;
        this.hasta = hasta;
    }

    public List<Abono> getAbonos() {
        return abonos;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public long getCantidad() {
        return cantidad;
    }

    public LocalDate getDesde() {
        return desde;
    }

    public LocalDate getHasta() {
        return hasta;
    }
}
