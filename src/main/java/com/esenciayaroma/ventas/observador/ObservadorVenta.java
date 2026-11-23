package com.esenciayaroma.ventas.observador;

/**
 * Observer: cualquier clase que quiera enterarse de que se registró
 * una venta implementa esta interfaz y se marca como @Component.
 */
public interface ObservadorVenta {

    void alRegistrarseVenta(EventoVenta evento);
}
