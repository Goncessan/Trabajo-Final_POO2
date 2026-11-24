package com.esenciayaroma.inventario;

import java.math.BigDecimal;

public class ValoracionPorVenta
        implements EstrategiaValoracion {

    @Override
    public BigDecimal calcularValor(Producto producto) {

        BigDecimal precio = producto.getPrecioVenta();

        int stock = producto.getStock();

        return precio.multiply(
                BigDecimal.valueOf(stock)
        );
    }
}
