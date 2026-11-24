
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
public class EstrategiaCobranzaMensual
        implements EstrategiaPeriodoCobranza {

    @Override
    public String getCodigo() {
        return "mensual";
    }

    @Override
    public List<ResumenCobroPorPeriodo> agrupar(
            List<Abono> abonos) {

        Map<YearMonth, BigDecimal> totales = new TreeMap<>();
        Map<YearMonth, Long> cantidades = new TreeMap<>();

        for (Abono abono : abonos) {

            YearMonth mes = YearMonth.from(
                    abono.getFechaRegistro()
            );

            totales.merge(
                    mes,
                    abono.getMonto(),
                    BigDecimal::add
            );

            cantidades.merge(mes, 1L, Long::sum);
        }

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("MM/yyyy");

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
