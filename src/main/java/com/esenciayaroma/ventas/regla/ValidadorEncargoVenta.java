package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.inventario.Producto;
import com.esenciayaroma.ventas.Cliente;
import com.esenciayaroma.ventas.PedidoCliente;
import com.esenciayaroma.ventas.soporte.MensajesVenta;
import org.springframework.stereotype.Component;

/**
 * Comprueba que una venta asociada a un encargo sea coherente.
 */
@Component
public class ValidadorEncargoVenta {

    public void validar(
            PedidoCliente pedido,
            Cliente cliente,
            Producto producto,
            int cantidad) {

        if (!pedido.getCliente().getId().equals(cliente.getId())) {
            throw new IllegalArgumentException(
                    MensajesVenta.ENCARGO_OTRO_CLIENTE
            );
        }

        if (pedido.getMarca() != producto.getMarca()) {
            throw new IllegalArgumentException(
                    MensajesVenta.MARCA_DISTINTA
            );
        }

        if (cantidad > pedido.getCantidadPendiente()) {
            throw new IllegalArgumentException(
                    MensajesVenta.CANTIDAD_SUPERA_PENDIENTE
            );
        }
    }
}
