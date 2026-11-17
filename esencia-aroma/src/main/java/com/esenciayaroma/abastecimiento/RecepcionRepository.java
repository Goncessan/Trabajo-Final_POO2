
package com.esenciayaroma.abastecimiento;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

@NullMarked
public interface RecepcionRepository
        extends JpaRepository<RecepcionPedido, Long> {

    @EntityGraph(attributePaths = {
            "detalles",
            "detalles.producto"
    })
    List<RecepcionPedido>
    findAllByOrderByFechaRecepcionDescIdDesc();

    boolean existsByDetallesProductoId(Long productoId);
}
