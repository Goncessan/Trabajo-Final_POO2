
package com.esenciayaroma.cobranzas;

public enum MedioPago {

    EFECTIVO("Efectivo"),
    YAPE("Yape"),
    PLIN("Plin"),
    TRANSFERENCIA("Transferencia bancaria"),
    TARJETA("Tarjeta"),
    OTRO("Otro"),
    NO_ESPECIFICADO("No especificado");

    private final String nombre;

    MedioPago(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
