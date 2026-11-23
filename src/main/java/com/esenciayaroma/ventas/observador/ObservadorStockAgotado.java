package com.esenciayaroma.ventas.observador;

import com.esenciayaroma.ventas.alerta.AlertaStockServicio;
import com.esenciayaroma.ventas.alerta.ReglaStockAgotado;
import com.esenciayaroma.ventas.alerta.TipoAlerta;
import org.springframework.stereotype.Component;

/**
 * Genera una alerta cuando el producto se quedó sin unidades.
 */
@Component
public class ObservadorStockAgotado implements ObservadorVenta {

    private final ReglaStockAgotado regla;
    private final AlertaStockServicio alertas;

    public ObservadorStockAgotado(
            ReglaStockAgotado regla,
            AlertaStockServicio alertas) {

        this.regla = regla;
        this.alertas = alertas;
    }

    @Override
    public void alRegistrarseVenta(EventoVenta evento) {

        if (regla.cumple(evento.stockActual())) {
            alertas.crearSiNoExiste(evento, TipoAlerta.STOCK_AGOTADO);
        }
    }
}
