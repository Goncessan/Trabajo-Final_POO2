package com.esenciayaroma.ventas.alerta;

public enum TipoAlerta {

    STOCK_BAJO("Stock bajo"),
    STOCK_AGOTADO("Stock agotado");

    private final String nombre;

    TipoAlerta(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
