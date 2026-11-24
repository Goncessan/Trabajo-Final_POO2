
package com.esenciayaroma.inventario;

import java.util.List;

public class ResumenInventario {

    private final List<Producto> productos;
    private final long totalProductos;
    private final List<String> categorias;

    public ResumenInventario(
            List<Producto> productos,
            long totalProductos,
            List<String> categorias) {

        this.productos = productos;
        this.totalProductos = totalProductos;
        this.categorias = categorias;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public long getTotalProductos() {
        return totalProductos;
    }

    public List<String> getCategorias() {
        return categorias;
    }
}
