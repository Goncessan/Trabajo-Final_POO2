package com.esenciayaroma.ventas.alerta;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class AlertaNoEncontradaException extends ResponseStatusException {

    public AlertaNoEncontradaException() {
        super(HttpStatus.NOT_FOUND, "Alerta no encontrada");
    }
}
