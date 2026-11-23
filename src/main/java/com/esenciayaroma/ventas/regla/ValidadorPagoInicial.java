package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.ventas.soporte.MensajesVenta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ValidadorPagoInicial {

    public void validar(BigDecimal pagoInicial, BigDecimal total) {

        if (pagoInicial.compareTo(total) > 0) {
            throw new IllegalArgumentException(
                    MensajesVenta.PAGO_SUPERA_TOTAL
            );
        }
    }
}
