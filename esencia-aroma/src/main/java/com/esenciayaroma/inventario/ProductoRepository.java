
package com.esenciayaroma.inventario;

import jakarta.persistence.LockModeType;
import org.jspecify.annotations.NullMarked;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface ProductoRepository
        extends JpaRepository<Producto, Long> {

    List<Producto> findAllByOrderByNombreAsc();

    boolean existsByCodigoIgnoreCase(String codigo);

    boolean existsByCodigoIgnoreCaseAndIdNot(
            String codigo,
            Long id
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Producto p WHERE p.id = :id")
    Optional<Producto> buscarParaMovimiento(
            @Param("id") Long id
    );
}
