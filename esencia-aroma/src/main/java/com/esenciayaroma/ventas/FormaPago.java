
package com.esenciayaroma.ventas;

public enum FormaPago {

    CONTADO("Contado"),
    CREDITO("Crédito");

    private final String nombre;

    FormaPago(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
