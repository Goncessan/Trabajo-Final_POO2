
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuentaPorCobrar {

    private final Long ventaId;
    private final String cliente;
    private final LocalDateTime fechaVenta;
    private final BigDecimal total;
    private final BigDecimal montoPagado;
    private final BigDecimal saldo;
    private final String estado;

    public CuentaPorCobrar(
            Long ventaId,
            String cliente,
            LocalDateTime fechaVenta,
            BigDecimal total,
            BigDecimal montoPagado,
            BigDecimal saldo,
            String estado) {

        this.ventaId = ventaId;
        this.cliente = cliente;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.montoPagado = montoPagado;
        this.saldo = saldo;
        this.estado = estado;
    }

    public Long getVentaId() { return ventaId; }
    public String getCliente() { return cliente; }
    public LocalDateTime getFechaVenta() { return fechaVenta; }
    public BigDecimal getTotal() { return total; }
    public BigDecimal getMontoPagado() { return montoPagado; }
    public BigDecimal getSaldo() { return saldo; }
    public String getEstado() { return estado; }
}
