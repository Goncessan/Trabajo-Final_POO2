
package com.esenciayaroma.inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ConteoInventarioRepository
        extends JpaRepository<ConteoInventario, Long> {

    List<ConteoInventario>
    findAllByOrderByFechaRegistroDescIdDesc();
}
