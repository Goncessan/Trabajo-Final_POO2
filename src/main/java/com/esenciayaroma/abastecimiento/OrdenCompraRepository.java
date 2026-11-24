package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface OrdenCompraRepository
        extends JpaRepository<OrdenCompra, Long> {

    List<OrdenCompra> findByEstado(EstadoOrdenCompra estado);

    boolean existsByPedido_Id(Long pedidoId);
}
