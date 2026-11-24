
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class ValidadorPago {

    public void validar(
            BigDecimal monto,
            MedioPago medioPago,
            String referencia,
            String observacion) {

        if (monto == null ||
                monto.compareTo(BigDecimal.ZERO) <= 0) {

            throw new IllegalArgumentException(
                    "El importe debe ser mayor que cero."
            );
        }

        if (monto.scale() > 2 ||
                monto.precision() - monto.scale() > 10) {

            throw new IllegalArgumentException(
                    "El importe debe tener como máximo "
                            + "10 dígitos enteros y 2 decimales."
            );
        }

        if (medioPago == null ||
                medioPago == MedioPago.NO_ESPECIFICADO) {

            throw new IllegalArgumentException(
                    "Selecciona un medio de pago válido."
            );
        }

        if (referencia != null &&
                referencia.trim().length() > 100) {

            throw new IllegalArgumentException(
                    "La referencia no puede superar 100 caracteres."
            );
        }

        if (observacion != null &&
                observacion.trim().length() > 200) {

            throw new IllegalArgumentException(
                    "La observación no puede superar 200 caracteres."
            );
        }
    }
}
