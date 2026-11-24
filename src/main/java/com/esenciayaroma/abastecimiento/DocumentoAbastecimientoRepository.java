package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DocumentoAbastecimientoRepository
        extends JpaRepository<DocumentoAbastecimiento, Long> {

    List<DocumentoAbastecimiento> findByProveedorId(Long proveedorId);

    List<DocumentoAbastecimiento> findByTipo(TipoDocumento tipo);
}
