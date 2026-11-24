package com.esenciayaroma.ventas.alerta;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AlertaStockVentaRepository
        extends JpaRepository<AlertaStockVenta, Long> {

    boolean existsByProductoIdAndTipoAndAtendidaFalse(
            Long productoId,
            TipoAlertaStock tipo
    );

    List<AlertaStockVenta> findByAtendidaFalseOrderByFechaCreacionDesc();

    long countByAtendidaFalse();
}
