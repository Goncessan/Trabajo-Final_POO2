package com.esenciayaroma.ventas.concurrencia;

import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Toma y suelta el candado de un producto.
 */
@Component
public class BloqueoProducto {

    private final AdministradorBloqueos administrador;

    public BloqueoProducto(AdministradorBloqueos administrador) {
        this.administrador = administrador;
    }

    public ReentrantLock adquirir(Long productoId) {

        ReentrantLock candado = administrador.obtener(productoId);

        try {
            boolean obtenido = candado.tryLock(
                    ConfiguracionBloqueos.ESPERA_SEGUNDOS,
                    TimeUnit.SECONDS
            );

            if (!obtenido) {
                throw new VentaOcupadaException();
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new VentaOcupadaException();
        }

        return candado;
    }

    public void liberar(ReentrantLock candado) {

        if (candado.isHeldByCurrentThread()) {
            candado.unlock();
        }
    }
}
