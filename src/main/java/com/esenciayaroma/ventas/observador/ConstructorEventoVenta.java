package com.esenciayaroma.ventas.observador;

import com.esenciayaroma.inventario.Producto;
import com.esenciayaroma.ventas.Cliente;
import com.esenciayaroma.ventas.Venta;
import org.springframework.stereotype.Component;

/**
 * Arma el EventoVenta a partir de la venta guardada.
 */
@Component
public class ConstructorEventoVenta {

    public EventoVenta crear(
            Venta venta,
            Cliente cliente,
            Producto producto,
            int cantidad) {

        return new EventoVenta(
                venta.getId(),
                producto.getId(),
                producto.getNombre(),
                cantidad,
                producto.getStock(),
                producto.getStockMinimo(),
                venta.getFormaPago(),
                cliente.getNombre()
        );
    }
}
