package com.esenciayaroma.ventas.observador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Implementación del Subject. Spring le entrega automáticamente
 * todas las clases @Component que implementen ObservadorVenta,
 * así que agregar un observador nuevo no obliga a modificar esta clase.
 */
@Component
public class PublicadorVentas implements SujetoVentas {

    private static final Logger log =
            LoggerFactory.getLogger(PublicadorVentas.class);

    private final List<ObservadorVenta> observadores;

    public PublicadorVentas(List<ObservadorVenta> observadores) {
        this.observadores = observadores;
    }

    @Override
    public void notificar(EventoVenta evento) {

        for (ObservadorVenta observador : observadores) {
            try {
                observador.alRegistrarseVenta(evento);
            } catch (RuntimeException e) {
                // Un observador con problemas no debe impedir la venta.
                log.warn("El observador {} falló: {}",
                        observador.getClass().getSimpleName(),
                        e.getMessage());
            }
        }
    }
}
