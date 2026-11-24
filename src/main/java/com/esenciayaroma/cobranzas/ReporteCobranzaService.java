
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@Service
public class ReporteCobranzaService {

    private final AbonoRepository abonoRepository;

    public ReporteCobranzaService(
            AbonoRepository abonoRepository) {

        this.abonoRepository = abonoRepository;
    }

    @Transactional(readOnly = true)
    public ReporteCobranza generar(
            FiltroReporteCobranza filtro) {

        if (filtro.getDesde() != null
                && filtro.getHasta() != null
                && filtro.getHasta().isBefore(filtro.getDesde())) {

            throw new IllegalArgumentException(
                    "La fecha final no puede ser anterior a la inicial."
            );
        }

        List<Abono> abonos = abonoRepository.findAll()
                .stream()

                .filter(a ->
                        filtro.getDesde() == null
                                || !a.getFechaRegistro()
                                .toLocalDate()
                                .isBefore(filtro.getDesde())
                )

                .filter(a ->
                        filtro.getHasta() == null
                                || !a.getFechaRegistro()
                                .toLocalDate()
                                .isAfter(filtro.getHasta())
                )

                .sorted(
                        Comparator.comparing(
                                Abono::getFechaRegistro
                        ).reversed()
                )

                .toList();

        BigDecimal total = abonos.stream()
                .map(Abono::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ReporteCobranzaBuilder()
                .conAbonos(abonos)
                .conTotal(total)
                .conCantidad(abonos.size())
                .conDesde(filtro.getDesde())
                .conHasta(filtro.getHasta())
                .build();
    }
}
