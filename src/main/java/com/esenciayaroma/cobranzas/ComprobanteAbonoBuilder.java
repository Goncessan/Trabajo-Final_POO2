
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class ComprobanteAbonoBuilder {

    private Long numero;
    private Long ventaId;
    private LocalDateTime fecha;
    private BigDecimal monto;
    private String medioPago;
    private String referencia;
    private String observacion;

    public ComprobanteAbonoBuilder conNumero(Long numero) {
        this.numero = numero;
        return this;
    }

    public ComprobanteAbonoBuilder conVenta(Long ventaId) {
        this.ventaId = ventaId;
        return this;
    }

    public ComprobanteAbonoBuilder conFecha(
            LocalDateTime fecha) {
        this.fecha = fecha;
        return this;
    }

    public ComprobanteAbonoBuilder conMonto(BigDecimal monto) {
        this.monto = monto;
        return this;
    }

    public ComprobanteAbonoBuilder conMedioPago(String medioPago) {
        this.medioPago = medioPago;
        return this;
    }

    public ComprobanteAbonoBuilder conReferencia(
            String referencia) {
        this.referencia = referencia;
        return this;
    }

    public ComprobanteAbonoBuilder conObservacion(
            String observacion) {
        this.observacion = observacion;
        return this;
    }

    public ComprobanteAbono build() {

        if (numero == null || ventaId == null
                || fecha == null || monto == null) {

            throw new IllegalStateException(
                    "Faltan datos obligatorios del comprobante."
            );
        }

        return new ComprobanteAbono(
                numero,
                ventaId,
                fecha,
                monto,
                medioPago,
                referencia,
                observacion
        );
    }
}
