
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.FormaPago;
import com.esenciayaroma.ventas.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class VencimientoCobranzaFactory {

    public VencimientoCobranza crear(
            Venta venta,
            VencimientoFormulario formulario) {

        if (venta == null || formulario == null) {
            throw new IllegalArgumentException(
                    "Los datos del vencimiento son obligatorios."
            );
        }

        if (venta.getFormaPago() != FormaPago.CREDITO) {
            throw new IllegalArgumentException(
                    "Solo se pueden programar vencimientos "
                            + "para ventas al crédito."
            );
        }

        if (venta.getSaldo() == null ||
                venta.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "La venta no tiene saldo pendiente."
            );
        }

        if (formulario.getFechaVencimiento() == null) {
            throw new IllegalArgumentException(
                    "Debe indicar una fecha de vencimiento."
            );
        }

        String observacion = formulario.getObservacion() == null
                ? ""
                : formulario.getObservacion().trim();

        return new VencimientoCobranza(
                venta,
                formulario.getFechaVencimiento(),
                observacion
        );
    }
}
