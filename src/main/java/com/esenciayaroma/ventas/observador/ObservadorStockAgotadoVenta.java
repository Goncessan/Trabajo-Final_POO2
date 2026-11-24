package com.esenciayaroma.ventas.observador;

import com.esenciayaroma.ventas.alerta.AlertaStockVentaServicio;
import com.esenciayaroma.ventas.alerta.ReglaStockAgotado;
import com.esenciayaroma.ventas.alerta.TipoAlertaStock;
import org.springframework.stereotype.Component;

/**
 * Genera una alerta cuando el producto se quedó sin unidades.
 */
@Component
public class ObservadorStockAgotadoVenta implements ObservadorVenta {

    private final ReglaStockAgotado regla;
    private final AlertaStockVentaServicio alertas;

    public ObservadorStockAgotadoVenta(
            ReglaStockAgotado regla,
            AlertaStockVentaServicio alertas) {

        this.regla = regla;
        this.alertas = alertas;
    }

    @Override
    public void alRegistrarseVenta(EventoVenta evento) {

        if (regla.cumple(evento.stockActual())) {
            alertas.crearSiNoExiste(evento, TipoAlertaStock.STOCK_AGOTADO);
        }
    }
}
