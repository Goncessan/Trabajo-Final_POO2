
package com.esenciayaroma.ventas;

import jakarta.persistence.LockModeType;
import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@NullMarked
public interface VentaRepository
        extends JpaRepository<Venta, Long> {

    @EntityGraph(attributePaths = {
            "cliente",
            "producto",
            "pedidoCliente"
    })
    List<Venta> findAllByOrderByFechaRegistroDescIdDesc();

    @EntityGraph(attributePaths = {
            "cliente",
            "producto"
    })
    List<Venta> findAllByFormaPagoOrderByFechaRegistroDescIdDesc(
            FormaPago formaPago
    );

    @EntityGraph(attributePaths = {
            "cliente",
            "producto",
            "pedidoCliente"
    })
    @Query("SELECT v FROM Venta v WHERE v.id = :id")
    Optional<Venta> buscarDetalle(@Param("id") Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT v FROM Venta v WHERE v.id = :id")
    Optional<Venta> buscarParaAbono(@Param("id") Long id);
}
