
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstadisticaCobranzaService {

    private final AbonoRepository abonoRepository;
    private final List<EstrategiaPeriodoCobranza> estrategias;

    public EstadisticaCobranzaService(
            AbonoRepository abonoRepository,
            List<EstrategiaPeriodoCobranza> estrategias) {

        this.abonoRepository = abonoRepository;
        this.estrategias = estrategias;
    }

    @Transactional(readOnly = true)
    public EstadisticaCobranza generar(String periodo) {

        List<Abono> abonos = abonoRepository.findAll();

        BigDecimal total = abonos.stream()
                .map(Abono::getMonto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, BigDecimal> totales = new LinkedHashMap<>();
        Map<String, Long> cantidades = new LinkedHashMap<>();

        for (Abono abono : abonos) {

            String medio = abono.getMedioPago() == null
                    ? "No especificado"
                    : abono.getMedioPago().getNombre();

            totales.merge(
                    medio,
                    abono.getMonto(),
                    BigDecimal::add
            );

            cantidades.merge(medio, 1L, Long::sum);
        }

        List<ResumenCobroPorMedio> porMedio =
                new ArrayList<>();

        totales.forEach((medio, importe) ->
                porMedio.add(
                        new ResumenCobroPorMedio(
                                medio,
                                cantidades.get(medio),
                                importe
                        )
                )
        );

        String codigo = "diario".equalsIgnoreCase(periodo)
                ? "diario"
                : "mensual";

        EstrategiaPeriodoCobranza estrategia =
                estrategias.stream()
                        .filter(e -> e.getCodigo().equals(codigo))
                        .findFirst()
                        .orElseThrow(() ->
                                new IllegalStateException(
                                        "Estrategia no encontrada."
                                )
                        );

        return new EstadisticaCobranza(
                total,
                abonos.size(),
                porMedio,
                estrategia.agrupar(abonos),
                codigo
        );
    }
}
