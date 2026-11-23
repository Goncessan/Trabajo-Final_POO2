package com.esenciayaroma.ventas.observador;

import com.esenciayaroma.ventas.alerta.AlertaStockServicio;
import com.esenciayaroma.ventas.alerta.ReglaStockBajo;
import com.esenciayaroma.ventas.alerta.TipoAlerta;
import org.springframework.stereotype.Component;

/**
 * Genera una alerta cuando el stock quedó en el mínimo o por debajo
 * (pero todavía hay unidades).
 */
@Component
public class ObservadorStockBajo implements ObservadorVenta {

    private final ReglaStockBajo regla;
    private final AlertaStockServicio alertas;

    public ObservadorStockBajo(
            ReglaStockBajo regla,
            AlertaStockServicio alertas) {

        this.regla = regla;
        this.alertas = alertas;
    }

    @Override
    public void alRegistrarseVenta(EventoVenta evento) {

        if (regla.cumple(evento.stockActual(), evento.stockMinimo())) {
            alertas.crearSiNoExiste(evento, TipoAlerta.STOCK_BAJO);
        }
    }
}
