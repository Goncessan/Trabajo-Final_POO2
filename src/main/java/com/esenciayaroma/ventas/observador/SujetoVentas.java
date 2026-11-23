package com.esenciayaroma.ventas.observador;

/**
 * Subject: quien avisa a los observadores cuando ocurre una venta.
 */
public interface SujetoVentas {

    void notificar(EventoVenta evento);
}
