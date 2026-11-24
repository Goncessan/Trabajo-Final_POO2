
package com.esenciayaroma.inventario;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class NotificadorStock {

    private static final Logger logger =
            LoggerFactory.getLogger(NotificadorStock.class);

    private final List<ObservadorStock> observadores;

    public NotificadorStock(
            List<ObservadorStock> observadores) {

        this.observadores = List.copyOf(observadores);
    }

    public void notificar(EventoCambioStock evento) {

        for (ObservadorStock observador : observadores) {

            try {
                observador.actualizar(evento);

            } catch (Exception e) {
                logger.error(
                        "Error al notificar al observador de stock",
                        e
                );
            }
        }
    }
}
