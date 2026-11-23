
package com.esenciayaroma.cobranzas;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@NullMarked
public interface AbonoRepository
        extends JpaRepository<Abono, Long> {

    List<Abono> findAllByVentaIdOrderByFechaRegistroDescIdDesc(
            Long ventaId
    );
}
