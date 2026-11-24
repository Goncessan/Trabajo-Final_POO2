package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CotizacionProveedorRepository
        extends JpaRepository<CotizacionProveedor, Long> {

    List<CotizacionProveedor> findByProveedorId(Long proveedorId);

    List<CotizacionProveedor> findByEstado(EstadoCotizacion estado);
}
