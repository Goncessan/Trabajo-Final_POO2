package com.esenciayaroma.ventas.alerta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaStockRepository
        extends JpaRepository<AlertaStock, Long> {

    boolean existsByProductoIdAndTipoAndAtendidaFalse(
            Long productoId,
            TipoAlerta tipo
    );

    List<AlertaStock> findByAtendidaFalseOrderByFechaCreacionDesc();

    long countByAtendidaFalse();
}
