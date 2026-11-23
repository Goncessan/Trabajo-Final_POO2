
package com.esenciayaroma.ventas;

import jakarta.persistence.LockModeType;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

@NullMarked
public interface PedidoClienteRepository
        extends JpaRepository<PedidoCliente, Long> {

    @EntityGraph(attributePaths = "cliente")
    List<PedidoCliente> findAllByOrderByFechaRegistroDescIdDesc();

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM PedidoCliente p WHERE p.id = :id")
    Optional<PedidoCliente> buscarParaEntrega(
            @Param("id") Long id
    );
}
