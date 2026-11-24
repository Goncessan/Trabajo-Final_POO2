
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Service
public class ReporteMediosPagoService {

    private final AbonoRepository abonoRepository;

    public ReporteMediosPagoService(
            AbonoRepository abonoRepository) {

        this.abonoRepository = abonoRepository;
    }

    @Transactional(readOnly = true)
    public List<Abono> listarPagos() {

        return abonoRepository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    @Transactional(readOnly = true)
    public List<ResumenMedioPago> generarResumen() {

        List<Abono> abonos = listarPagos();

        return Arrays.stream(MedioPago.values())
                .map(medio -> {

                    List<Abono> filtrados = abonos.stream()
                            .filter(abono ->
                                    abono.getMedioPago() == medio
                            )
                            .toList();

                    BigDecimal importe = filtrados.stream()
                            .map(Abono::getMonto)
                            .reduce(
                                    BigDecimal.ZERO,
                                    BigDecimal::add
                            );

                    return new ResumenMedioPago(
                            medio,
                            filtrados.size(),
                            importe
                    );
                })
                .filter(resumen ->
                        resumen.getCantidadOperaciones() > 0
                )
                .toList();
    }
}
