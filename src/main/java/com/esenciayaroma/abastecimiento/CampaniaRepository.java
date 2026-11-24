package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CampaniaRepository
        extends JpaRepository<Campania, Long> {

    List<Campania> findByMarca(
            com.esenciayaroma.inventario.Marca marca
    );

    List<Campania> findByEstado(EstadoCampania estado);
}

