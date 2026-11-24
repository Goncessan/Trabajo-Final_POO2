
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Component;

@Component
public class ObservadorStockBajo implements ObservadorStock {

    private final AlertaStockService alertaStockService;

    public ObservadorStockBajo(
            AlertaStockService alertaStockService) {

        this.alertaStockService = alertaStockService;
    }

    @Override
    public void actualizar(EventoCambioStock evento) {

        boolean antesNormal =
                evento.getStockAnterior()
                        > evento.getStockMinimo();

        boolean ahoraBajo =
                evento.getStockPosterior()
                        <= evento.getStockMinimo();

        if (antesNormal && ahoraBajo) {

            alertaStockService.registrarAlerta(evento);
        }
    }
}
