
package com.esenciayaroma.cobranzas;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface VencimientoCobranzaRepository
        extends JpaRepository<VencimientoCobranza, Long> {

    Optional<VencimientoCobranza> findByVentaId(Long ventaId);

    List<VencimientoCobranza>
    findAllByOrderByFechaVencimientoAsc();
}
