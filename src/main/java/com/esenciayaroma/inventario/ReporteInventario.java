
package com.esenciayaroma.inventario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReporteInventario {

    private final long totalProductos;
    private final long totalUnidades;
    private final int totalStockBajo;

    private final BigDecimal valorCosto;
    private final BigDecimal valorVenta;

    private final List<Producto> productosBajoStock;
    private final LocalDateTime fechaGeneracion;

    public ReporteInventario(
            long totalProductos,
            long totalUnidades,
            int totalStockBajo,
            BigDecimal valorCosto,
            BigDecimal valorVenta,
            List<Producto> productosBajoStock,
            LocalDateTime fechaGeneracion) {

        this.totalProductos = totalProductos;
        this.totalUnidades = totalUnidades;
        this.totalStockBajo = totalStockBajo;
        this.valorCosto = valorCosto;
        this.valorVenta = valorVenta;
        this.productosBajoStock =
                List.copyOf(productosBajoStock);
        this.fechaGeneracion = fechaGeneracion;
    }

    public long getTotalProductos() {
        return totalProductos;
    }

    public long getTotalUnidades() {
        return totalUnidades;
    }

    public int getTotalStockBajo() {
        return totalStockBajo;
    }

    public BigDecimal getValorCosto() {
        return valorCosto;
    }

    public BigDecimal getValorVenta() {
        return valorVenta;
    }

    public List<Producto> getProductosBajoStock() {
        return productosBajoStock;
    }

    public LocalDateTime getFechaGeneracion() {
        return fechaGeneracion;
    }

    public String getFechaFormateada() {
        return fechaGeneracion.format(
                DateTimeFormatter.ofPattern(
                        "dd/MM/yyyy HH:mm"
                )
        );
    }
}
