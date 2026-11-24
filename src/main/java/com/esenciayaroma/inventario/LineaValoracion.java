
package com.esenciayaroma.inventario;

import java.math.BigDecimal;

public class LineaValoracion {

    private final String codigo;
    private final String nombre;
    private final int stock;
    private final BigDecimal precioUnitario;
    private final BigDecimal valorTotal;

    public LineaValoracion(
            String codigo,
            String nombre,
            int stock,
            BigDecimal precioUnitario,
            BigDecimal valorTotal) {

        this.codigo = codigo;
        this.nombre = nombre;
        this.stock = stock;
        this.precioUnitario = precioUnitario;
        this.valorTotal = valorTotal;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStock() {
        return stock;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }
}
