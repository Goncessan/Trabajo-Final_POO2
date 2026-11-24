
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class EstrategiaCobranzaDiaria
        implements EstrategiaPeriodoCobranza {

    @Override
    public String getCodigo() {
        return "diario";
    }

    @Override
    public List<ResumenCobroPorPeriodo> agrupar(
            List<Abono> abonos) {

        Map<LocalDate, BigDecimal> totales = new TreeMap<>();
        Map<LocalDate, Long> cantidades = new TreeMap<>();

        for (Abono abono : abonos) {

            LocalDate fecha = abono.getFechaRegistro()
                    .toLocalDate();

            totales.merge(
                    fecha,
                    abono.getMonto(),
                    BigDecimal::add
            );

            cantidades.merge(fecha, 1L, Long::sum);
        }

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return totales.entrySet()
                .stream()
                .map(e -> new ResumenCobroPorPeriodo(
                        e.getKey().format(formato),
                        e.getValue(),
                        cantidades.get(e.getKey())
                ))
                .toList();
    }
}
