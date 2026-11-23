
package com.esenciayaroma.inventario;

public enum Marca {

    AVON("Avon"),
    NATURA("Natura"),
    YANBAL("Yanbal"),
    ESIKA("Ésika"),
    CYZONE("Cyzone"),
    LBEL("L'Bel");

    private final String nombre;

    Marca(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
