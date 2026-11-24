package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SeguimientoAbastecimientoRepository
        extends JpaRepository<SeguimientoAbastecimiento, Long> {

    List<SeguimientoAbastecimiento> findByEstado(
            EstadoSeguimiento estado
    );

    List<SeguimientoAbastecimiento> findByPedidoId(Long pedidoId);
}
