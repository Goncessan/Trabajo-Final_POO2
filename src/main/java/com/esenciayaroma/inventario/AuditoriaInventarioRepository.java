package com.esenciayaroma.inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditoriaInventarioRepository
        extends JpaRepository<AuditoriaInventario, Long> {

    List<AuditoriaInventario>
    findAllByOrderByFechaRegistroDescIdDesc();
}
