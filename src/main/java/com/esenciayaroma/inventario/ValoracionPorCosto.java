package com.esenciayaroma.inventario;

import java.math.BigDecimal;

public class ValoracionPorCosto
        implements EstrategiaValoracion {

    @Override
    public BigDecimal calcularValor(Producto producto) {

        BigDecimal precio = producto.getPrecioCompra();

        int stock = producto.getStock();

        return precio.multiply(
                BigDecimal.valueOf(stock)
        );
    }
}
