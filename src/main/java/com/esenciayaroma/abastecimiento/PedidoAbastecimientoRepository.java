package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PedidoAbastecimientoRepository
        extends JpaRepository<PedidoAbastecimiento, Long> {

    List<PedidoAbastecimiento>
    findByEstado(EstadoPedidoAbastecimiento estado);

    List<PedidoAbastecimiento>
    findByProveedorId(Long proveedorId);
}

