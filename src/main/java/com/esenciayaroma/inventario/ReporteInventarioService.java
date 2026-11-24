package com.esenciayaroma.inventario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReporteInventarioService {

    private final ProductoRepository productoRepository;

    public ReporteInventarioService(
            ProductoRepository productoRepository) {

        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public ReporteInventario generarReporte() {

        // 1. Obtener los productos desde H2
        List<Producto> productos = productoRepository
                .findAllByOrderByNombreAsc();

        // 2. Contar productos registrados
        long totalProductos = productos.size();

        // 3. Calcular unidades disponibles
        long totalUnidades = productos.stream()
                .mapToLong(Producto::getStock)
                .sum();

        // 4. Identificar productos con stock bajo
        List<Producto> productosBajoStock = productos
                .stream()
                .filter(producto ->
                        producto.getStock()
                                <= producto.getStockMinimo()
                )
                .toList();

        int totalStockBajo =
                productosBajoStock.size();

        // 5. Calcular valor total al costo
        BigDecimal valorCosto = productos.stream()
                .map(producto ->
                        producto.getPrecioCompra()
                                .multiply(
                                        BigDecimal.valueOf(
                                                producto.getStock()
                                        )
                                )
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        // 6. Calcular valor total de venta
        BigDecimal valorVenta = productos.stream()
                .map(producto ->
                        producto.getPrecioVenta()
                                .multiply(
                                        BigDecimal.valueOf(
                                                producto.getStock()
                                        )
                                )
                )
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        // 7. Construir el reporte con Builder
        return new ReporteInventarioBuilder()
                .conTotalProductos(totalProductos)
                .conTotalUnidades(totalUnidades)
                .conTotalStockBajo(totalStockBajo)
                .conValorCosto(valorCosto)
                .conValorVenta(valorVenta)
                .conProductosBajoStock(productosBajoStock)
                .conFechaGeneracion(LocalDateTime.now())
                .build();
    }
}
