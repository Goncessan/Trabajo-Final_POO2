
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ValoracionInventarioService {

    private final ProductoRepository productoRepository;

    public ValoracionInventarioService(
            ProductoRepository productoRepository) {

        this.productoRepository = productoRepository;
    }

    @Transactional(readOnly = true)
    public ResumenValoracion calcularValoracion(
            String tipo) {

        // Elegir la estrategia solicitada
        EstrategiaValoracion estrategia;

        if ("venta".equalsIgnoreCase(tipo)) {
            estrategia = new ValoracionPorVenta();
        } else {
            estrategia = new ValoracionPorCosto();
        }

        // Obtener los productos desde H2
        List<Producto> productos = productoRepository
                .findAllByOrderByNombreAsc();

        // Generar una fila por producto
        List<LineaValoracion> lineas = productos
                .stream()
                .map(producto -> {

                    BigDecimal precioUnitario =
                            estrategia instanceof ValoracionPorVenta
                                    ? producto.getPrecioVenta()
                                    : producto.getPrecioCompra();

                    BigDecimal valor =
                            estrategia.calcularValor(producto);

                    return new LineaValoracion(
                            producto.getCodigo(),
                            producto.getNombre(),
                            producto.getStock(),
                            precioUnitario,
                            valor
                    );
                })
                .toList();

        // Calcular el valor total del inventario
        BigDecimal valorTotal = lineas.stream()
                .map(LineaValoracion::getValorTotal)
                .reduce(
                        BigDecimal.ZERO,
                        BigDecimal::add
                );

        // Calcular las unidades disponibles
        int totalUnidades = lineas.stream()
                .mapToInt(LineaValoracion::getStock)
                .sum();

        return new ResumenValoracion(
                lineas,
                valorTotal,
                totalUnidades,
                lineas.size()
        );
    }
}
