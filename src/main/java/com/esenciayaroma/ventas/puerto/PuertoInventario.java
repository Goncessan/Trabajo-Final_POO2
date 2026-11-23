package com.esenciayaroma.ventas.puerto;

import com.esenciayaroma.inventario.Producto;

import java.util.List;

/**
 * Lo único que el módulo de ventas necesita del módulo de inventario.
 * Así inventario no se modifica y ventas depende de una sola clase.
 */
public interface PuertoInventario {

    Producto obtenerProducto(Long productoId);

    List<Producto> listarProductos();

    void registrarSalida(Long productoId, int cantidad, String nombreCliente);
}
