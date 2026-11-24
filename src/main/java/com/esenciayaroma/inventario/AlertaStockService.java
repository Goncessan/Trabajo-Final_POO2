package com.esenciayaroma.inventario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class AlertaStockService {

    private final AlertaStockRepository alertaRepository;

    public AlertaStockService(
            AlertaStockRepository alertaRepository) {

        this.alertaRepository = alertaRepository;
    }

    // Guardar la alerta generada por Observer
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void registrarAlerta(EventoCambioStock evento) {

        AlertaStock alerta = new AlertaStock(evento);

        alertaRepository.save(alerta);
    }

    // Consultar el historial de alertas
    @Transactional(readOnly = true)
    public List<AlertaStock> listarAlertas() {

        return alertaRepository
                .findAllByOrderByFechaDescIdDesc();
    }

    // Obtener la última alerta si es reciente
    @Transactional(readOnly = true)
    public Optional<AlertaStock> obtenerUltimaAlertaReciente() {

        LocalDateTime limite =
                LocalDateTime.now().minusMinutes(5);

        return alertaRepository
                .findTopByOrderByFechaDescIdDesc()
                .filter(alerta ->
                        alerta.getFecha() != null
                                && !alerta.getFecha().isBefore(limite)
                );
    }
}
