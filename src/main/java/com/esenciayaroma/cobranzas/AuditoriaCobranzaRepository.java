
package com.esenciayaroma.cobranzas;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditoriaCobranzaRepository
        extends JpaRepository<AuditoriaCobranza, Long> {

    List<AuditoriaCobranza>
    findAllByOrderByFechaRegistroDescIdDesc();

    List<AuditoriaCobranza>
    findAllByVentaIdOrderByFechaRegistroDescIdDesc(
            Long ventaId
    );
}
