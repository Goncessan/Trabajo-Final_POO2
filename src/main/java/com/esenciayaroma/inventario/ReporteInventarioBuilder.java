
package com.esenciayaroma.inventario;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class ReporteInventarioBuilder {

    private long totalProductos;
    private long totalUnidades;
    private int totalStockBajo;

    private BigDecimal valorCosto = BigDecimal.ZERO;
    private BigDecimal valorVenta = BigDecimal.ZERO;

    private List<Producto> productosBajoStock =
            List.of();

    private LocalDateTime fechaGeneracion =
            LocalDateTime.now();

    public ReporteInventarioBuilder conTotalProductos(
            long totalProductos) {

        this.totalProductos = totalProductos;
        return this;
    }

    public ReporteInventarioBuilder conTotalUnidades(
            long totalUnidades) {

        this.totalUnidades = totalUnidades;
        return this;
    }

    public ReporteInventarioBuilder conTotalStockBajo(
            int totalStockBajo) {

        this.totalStockBajo = totalStockBajo;
        return this;
    }

    public ReporteInventarioBuilder conValorCosto(
            BigDecimal valorCosto) {

        this.valorCosto = valorCosto;
        return this;
    }

    public ReporteInventarioBuilder conValorVenta(
            BigDecimal valorVenta) {

        this.valorVenta = valorVenta;
        return this;
    }

    public ReporteInventarioBuilder conProductosBajoStock(
            List<Producto> productos) {

        this.productosBajoStock = List.copyOf(productos);
        return this;
    }

    public ReporteInventarioBuilder conFechaGeneracion(
            LocalDateTime fechaGeneracion) {

        this.fechaGeneracion = fechaGeneracion;
        return this;
    }

    public ReporteInventario build() {

        if (totalProductos < 0 ||
                totalUnidades < 0 ||
                totalStockBajo < 0) {

            throw new IllegalArgumentException(
                    "Los indicadores no pueden ser negativos."
            );
        }

        if (valorCosto == null ||
                valorVenta == null ||
                fechaGeneracion == null) {

            throw new IllegalArgumentException(
                    "Los datos del reporte son obligatorios."
            );
        }

        return new ReporteInventario(
                totalProductos,
                totalUnidades,
                totalStockBajo,
                valorCosto,
                valorVenta,
                productosBajoStock,
                fechaGeneracion
        );
    }
}
