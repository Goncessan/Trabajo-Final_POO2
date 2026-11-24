
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ComprobanteAbono {

    private final Long numero;
    private final Long ventaId;
    private final LocalDateTime fecha;
    private final BigDecimal monto;
    private final String medioPago;
    private final String referencia;
    private final String observacion;

    public ComprobanteAbono(
            Long numero,
            Long ventaId,
            LocalDateTime fecha,
            BigDecimal monto,
            String medioPago,
            String referencia,
            String observacion) {

        this.numero = numero;
        this.ventaId = ventaId;
        this.fecha = fecha;
        this.monto = monto;
        this.medioPago = medioPago;
        this.referencia = referencia;
        this.observacion = observacion;
    }

    public Long getNumero() { return numero; }
    public Long getVentaId() { return ventaId; }
    public LocalDateTime getFecha() { return fecha; }
    public BigDecimal getMonto() { return monto; }
    public String getMedioPago() { return medioPago; }
    public String getReferencia() { return referencia; }
    public String getObservacion() { return observacion; }
}
