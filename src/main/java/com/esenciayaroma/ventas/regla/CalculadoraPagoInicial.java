package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.ventas.FormaPago;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Al contado se paga todo; a crédito se usa el pago inicial indicado
 * (que no puede superar el total).
 */
@Component
public class CalculadoraPagoInicial {

    private final ValidadorPagoInicial validador;

    public CalculadoraPagoInicial(ValidadorPagoInicial validador) {
        this.validador = validador;
    }

    public BigDecimal calcular(
            FormaPago formaPago,
            BigDecimal total,
            BigDecimal pagoIndicado) {

        if (formaPago == FormaPago.CONTADO) {
            return total;
        }

        validador.validar(pagoIndicado, total);

        return pagoIndicado;
    }
}
