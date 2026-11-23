package com.esenciayaroma.ventas.regla;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CalculadoraTotalVenta {

    public BigDecimal calcular(BigDecimal precioUnitario, int cantidad) {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}
