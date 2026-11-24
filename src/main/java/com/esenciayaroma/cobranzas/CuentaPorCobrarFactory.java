
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.Venta;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CuentaPorCobrarFactory {

    public CuentaPorCobrar crear(Venta venta) {

        if (venta == null) {
            throw new IllegalArgumentException(
                    "La venta es obligatoria."
            );
        }

        BigDecimal pagado = venta.getMontoPagado() == null
                ? BigDecimal.ZERO
                : venta.getMontoPagado();

        BigDecimal saldo = venta.getSaldo() == null
                ? BigDecimal.ZERO
                : venta.getSaldo();

        String cliente = venta.getCliente() == null
                ? "Cliente no identificado"
                : venta.getCliente().getNombre();

        String estado = pagado.compareTo(BigDecimal.ZERO) > 0
                ? "ABONO PARCIAL"
                : "PENDIENTE";

        return new CuentaPorCobrarBuilder()
                .conVentaId(venta.getId())
                .conCliente(cliente)
                .conFecha(venta.getFechaRegistro())
                .conTotal(venta.getTotal())
                .conMontoPagado(pagado)
                .conSaldo(saldo)
                .conEstado(estado)
                .build();
    }
}
