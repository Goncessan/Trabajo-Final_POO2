
package com.esenciayaroma.inventario;

import java.math.BigDecimal;
import java.util.List;

public class ResumenValoracion {

    private final List<LineaValoracion> lineas;
    private final BigDecimal valorTotal;
    private final int totalUnidades;
    private final int totalProductos;

    public ResumenValoracion(
            List<LineaValoracion> lineas,
            BigDecimal valorTotal,
            int totalUnidades,
            int totalProductos) {

        this.lineas = List.copyOf(lineas);
        this.valorTotal = valorTotal;
        this.totalUnidades = totalUnidades;
        this.totalProductos = totalProductos;
    }

    public List<LineaValoracion> getLineas() {
        return lineas;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public int getTotalUnidades() {
        return totalUnidades;
    }

    public int getTotalProductos() {
        return totalProductos;
    }
}
