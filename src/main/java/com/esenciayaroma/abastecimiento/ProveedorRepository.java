package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProveedorRepository
        extends JpaRepository<Proveedor, Long> {

    List<Proveedor> findByActivoTrue();

    boolean existsByNombreIgnoreCase(String nombre);
}