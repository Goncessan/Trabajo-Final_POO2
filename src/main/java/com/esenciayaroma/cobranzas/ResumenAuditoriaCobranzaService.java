
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ResumenAuditoriaCobranzaService {

    private final AuditoriaCobranzaRepository repository;

    public ResumenAuditoriaCobranzaService(
            AuditoriaCobranzaRepository repository) {

        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public ResumenAuditoriaCobranza generarResumen() {

        List<AuditoriaCobranza> registros =
                repository.findAll();

        BigDecimal total = registros.stream()
                .map(AuditoriaCobranza::getMonto)
                .filter(monto -> monto != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long digitales = registros.stream()
                .filter(a ->
                        a.getMedioPago() == MedioPago.YAPE
                                || a.getMedioPago() == MedioPago.PLIN
                                || a.getMedioPago() == MedioPago.TRANSFERENCIA
                )
                .count();

        return new ResumenAuditoriaCobranza(
                registros.size(),
                total,
                digitales
        );
    }
}
