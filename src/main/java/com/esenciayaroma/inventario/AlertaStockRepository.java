package com.esenciayaroma.inventario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AlertaStockRepository
        extends JpaRepository<AlertaStock, Long> {

    // Obtener todas las alertas para el historial
    List<AlertaStock> findAllByOrderByFechaDescIdDesc();

    // Obtener la alerta más reciente
    Optional<AlertaStock> findTopByOrderByFechaDescIdDesc();
}
