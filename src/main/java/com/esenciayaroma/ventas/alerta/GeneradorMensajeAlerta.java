package com.esenciayaroma.ventas.alerta;

import com.esenciayaroma.ventas.observador.EventoVenta;
import org.springframework.stereotype.Component;

/**
 * Redacta el texto de cada alerta.
 */
@Component
public class GeneradorMensajeAlerta {

    public String crear(EventoVenta evento, TipoAlertaStock tipo) {

        if (tipo == TipoAlertaStock.STOCK_AGOTADO) {
            return "Stock agotado: " + evento.productoNombre()
                    + " ya no tiene unidades disponibles.";
        }

        return "Stock bajo: " + evento.productoNombre()
                + " tiene " + evento.stockActual()
                + " unidades (mínimo " + evento.stockMinimo() + ").";
    }
}
