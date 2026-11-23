package com.esenciayaroma.ventas.puerto;

import com.esenciayaroma.inventario.InventarioService;
import com.esenciayaroma.inventario.MovimientoInventarioService;
import com.esenciayaroma.inventario.Producto;
import com.esenciayaroma.inventario.ProductoRepository;
import com.esenciayaroma.ventas.regla.ConstructorMovimientoSalida;
import com.esenciayaroma.ventas.soporte.MensajesVenta;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementa el puerto usando las clases que ya existen en inventario.
 */
@Component
public class AdaptadorInventario implements PuertoInventario {

    private final ProductoRepository productoRepository;
    private final InventarioService inventarioService;
    private final MovimientoInventarioService movimientoService;
    private final ConstructorMovimientoSalida constructorSalida;

    public AdaptadorInventario(
            ProductoRepository productoRepository,
            InventarioService inventarioService,
            MovimientoInventarioService movimientoService,
            ConstructorMovimientoSalida constructorSalida) {

        this.productoRepository = productoRepository;
        this.inventarioService = inventarioService;
        this.movimientoService = movimientoService;
        this.constructorSalida = constructorSalida;
    }

    @Override
    public Producto obtenerProducto(Long productoId) {
        return productoRepository.findById(productoId)
                .orElseThrow(() -> new IllegalArgumentException(
                        MensajesVenta.PRODUCTO_NO_EXISTE
                ));
    }

    @Override
    public List<Producto> listarProductos() {
        return inventarioService.listarProductos();
    }

    @Override
    public void registrarSalida(
            Long productoId,
            int cantidad,
            String nombreCliente) {

        movimientoService.registrarMovimiento(
                constructorSalida.construir(productoId, cantidad, nombreCliente)
        );
    }
}
