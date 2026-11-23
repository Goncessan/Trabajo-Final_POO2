package com.esenciayaroma.ventas.concurrencia;

import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Guarda un candado por producto. Dos ventas del mismo producto usan
 * el mismo candado; ventas de productos distintos no se estorban.
 */
@Component
public class AdministradorBloqueos {

    private final ConcurrentHashMap<Long, ReentrantLock> bloqueos =
            new ConcurrentHashMap<>();

    public ReentrantLock obtener(Long productoId) {
        return bloqueos.computeIfAbsent(
                productoId,
                id -> new ReentrantLock()
        );
    }
}
