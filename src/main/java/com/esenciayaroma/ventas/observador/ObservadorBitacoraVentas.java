package com.esenciayaroma.ventas.observador;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Deja constancia de cada venta en el registro (log) de la aplicación.
 */
@Component
public class ObservadorBitacoraVentas implements ObservadorVenta {

    private static final Logger log =
            LoggerFactory.getLogger(ObservadorBitacoraVentas.class);

    @Override
    public void alRegistrarseVenta(EventoVenta evento) {

        log.info("Venta #{} registrada: {} x {} para {} ({}). Stock restante: {}",
                evento.ventaId(),
                evento.cantidad(),
                evento.productoNombre(),
                evento.clienteNombre(),
                evento.formaPago().getNombre(),
                evento.stockActual());
    }
}
