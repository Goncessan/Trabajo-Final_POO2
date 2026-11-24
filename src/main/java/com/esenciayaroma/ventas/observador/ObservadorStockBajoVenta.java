package com.esenciayaroma.ventas.observador;

import com.esenciayaroma.ventas.alerta.AlertaStockVentaServicio;
import com.esenciayaroma.ventas.alerta.ReglaStockBajo;
import com.esenciayaroma.ventas.alerta.TipoAlertaStock;
import org.springframework.stereotype.Component;

/**
 * Genera una alerta cuando el stock quedó en el mínimo o por debajo
 * (pero todavía hay unidades).
 */
@Component
public class ObservadorStockBajoVenta implements ObservadorVenta {

    private final ReglaStockBajo regla;
    private final AlertaStockVentaServicio alertas;

    public ObservadorStockBajoVenta(
            ReglaStockBajo regla,
            AlertaStockVentaServicio alertas) {

        this.regla = regla;
        this.alertas = alertas;
    }

    @Override
    public void alRegistrarseVenta(EventoVenta evento) {

        if (regla.cumple(evento.stockActual(), evento.stockMinimo())) {
            alertas.crearSiNoExiste(evento, TipoAlertaStock.STOCK_BAJO);
        }
    }
}
