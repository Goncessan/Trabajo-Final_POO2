package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.inventario.MovimientoFormulario;
import com.esenciayaroma.inventario.TipoMovimiento;
import com.esenciayaroma.ventas.soporte.MensajesVenta;
import org.springframework.stereotype.Component;

/**
 * Arma el movimiento de SALIDA que descuenta el stock de una venta.
 */
@Component
public class ConstructorMovimientoSalida {

    public MovimientoFormulario construir(
            Long productoId,
            int cantidad,
            String nombreCliente) {

        MovimientoFormulario movimiento = new MovimientoFormulario();

        movimiento.setProductoId(productoId);
        movimiento.setTipo(TipoMovimiento.SALIDA);
        movimiento.setCantidad(cantidad);
        movimiento.setMotivo(MensajesVenta.MOTIVO_VENTA + nombreCliente);

        return movimiento;
    }
}
