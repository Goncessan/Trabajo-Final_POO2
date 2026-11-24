
package com.esenciayaroma.inventario;

public class EventoCambioStock {

    private final Long productoId;
    private final String codigo;
    private final String nombre;
    private final int stockAnterior;
    private final int stockPosterior;
    private final int stockMinimo;

    public EventoCambioStock(
            Long productoId,
            String codigo,
            String nombre,
            int stockAnterior,
            int stockPosterior,
            int stockMinimo) {

        this.productoId = productoId;
        this.codigo = codigo;
        this.nombre = nombre;
        this.stockAnterior = stockAnterior;
        this.stockPosterior = stockPosterior;
        this.stockMinimo = stockMinimo;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getStockAnterior() {
        return stockAnterior;
    }

    public int getStockPosterior() {
        return stockPosterior;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }
}
