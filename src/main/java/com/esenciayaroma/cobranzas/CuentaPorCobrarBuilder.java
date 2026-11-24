
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CuentaPorCobrarBuilder {

    private Long ventaId;
    private String cliente;
    private LocalDateTime fechaVenta;
    private BigDecimal total;
    private BigDecimal montoPagado;
    private BigDecimal saldo;
    private String estado;

    public CuentaPorCobrarBuilder conVentaId(Long ventaId) {
        this.ventaId = ventaId;
        return this;
    }

    public CuentaPorCobrarBuilder conCliente(String cliente) {
        this.cliente = cliente;
        return this;
    }

    public CuentaPorCobrarBuilder conFecha(LocalDateTime fecha) {
        this.fechaVenta = fecha;
        return this;
    }

    public CuentaPorCobrarBuilder conTotal(BigDecimal total) {
        this.total = total;
        return this;
    }

    public CuentaPorCobrarBuilder conMontoPagado(BigDecimal monto) {
        this.montoPagado = monto;
        return this;
    }

    public CuentaPorCobrarBuilder conSaldo(BigDecimal saldo) {
        this.saldo = saldo;
        return this;
    }

    public CuentaPorCobrarBuilder conEstado(String estado) {
        this.estado = estado;
        return this;
    }

    public CuentaPorCobrar build() {

        if (ventaId == null || cliente == null
                || fechaVenta == null || total == null
                || montoPagado == null || saldo == null
                || estado == null) {

            throw new IllegalStateException(
                    "Faltan datos para construir la cuenta por cobrar."
            );
        }

        return new CuentaPorCobrar(
                ventaId,
                cliente,
                fechaVenta,
                total,
                montoPagado,
                saldo,
                estado
        );
    }
}
