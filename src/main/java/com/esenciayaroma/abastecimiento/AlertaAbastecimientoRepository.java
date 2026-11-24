package com.esenciayaroma.abastecimiento;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AlertaAbastecimientoRepository
        extends JpaRepository<AlertaAbastecimiento, Long> {

    List<AlertaAbastecimiento> findByAtendidaFalse();

    List<AlertaAbastecimiento> findByTipo(TipoAlerta tipo);
}
