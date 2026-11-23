package com.esenciayaroma.ventas.concurrencia;

import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Cuenta cuántas ventas se están procesando en este momento.
 * AtomicInteger es seguro cuando varios hilos lo modifican a la vez.
 */
@Component
public class ContadorVentasEnCurso {

    private final AtomicInteger enCurso = new AtomicInteger(0);

    public void sumar() {
        enCurso.incrementAndGet();
    }

    public void restar() {
        enCurso.decrementAndGet();
    }

    public int obtener() {
        return enCurso.get();
    }
}
