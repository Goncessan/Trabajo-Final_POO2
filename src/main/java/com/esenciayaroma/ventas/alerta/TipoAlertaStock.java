package com.esenciayaroma.ventas.alerta;

public enum TipoAlertaStock {

    STOCK_BAJO("Stock bajo"),
    STOCK_AGOTADO("Stock agotado");

    private final String nombre;

    TipoAlertaStock(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
