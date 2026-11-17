
package com.esenciayaroma.inventario;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@NullMarked
public interface MovimientoInventarioRepository
        extends JpaRepository<MovimientoInventario, Long> {

    @EntityGraph(attributePaths = "producto")
    List<MovimientoInventario> findAllByOrderByFechaDescIdDesc();

    boolean existsByProductoId(Long productoId);
}
