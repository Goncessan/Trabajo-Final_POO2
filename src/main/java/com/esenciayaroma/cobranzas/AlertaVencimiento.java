
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDate;

public class AlertaVencimiento {

    private final Long ventaId;
    private final String cliente;
    private final LocalDate fechaVencimiento;
    private final BigDecimal saldo;
    private final String estado;
    private final String observacion;

    public AlertaVencimiento(
            Long ventaId,
            String cliente,
            LocalDate fechaVencimiento,
            BigDecimal saldo,
            String estado,
            String observacion) {

        this.ventaId = ventaId;
        this.cliente = cliente;
        this.fechaVencimiento = fechaVencimiento;
        this.saldo = saldo;
        this.estado = estado;
        this.observacion = observacion;
    }

    public Long getVentaId() { return ventaId; }
    public String getCliente() { return cliente; }
    public LocalDate getFechaVencimiento() { return fechaVencimiento; }
    public BigDecimal getSaldo() { return saldo; }
    public String getEstado() { return estado; }
    public String getObservacion() { return observacion; }
}
