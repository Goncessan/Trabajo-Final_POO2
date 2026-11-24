package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface SolicitudAbastecimientoRepository
        extends JpaRepository<SolicitudAbastecimiento, Long> {

    List<SolicitudAbastecimiento> findByEstado(
            EstadoSolicitudAbastecimiento estado
    );

    List<SolicitudAbastecimiento> findBySolicitanteContainingIgnoreCase(
            String solicitante
    );
}
