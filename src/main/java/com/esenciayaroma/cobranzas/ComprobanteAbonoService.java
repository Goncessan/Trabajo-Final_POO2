
package com.esenciayaroma.cobranzas;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class ComprobanteAbonoService {

    private final AbonoRepository abonoRepository;

    public ComprobanteAbonoService(
            AbonoRepository abonoRepository) {

        this.abonoRepository = abonoRepository;
    }

    @Transactional(readOnly = true)
    public ComprobanteAbono generar(Long abonoId) {

        Abono abono = abonoRepository.findById(abonoId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Abono no encontrado"
                        )
                );

        return new ComprobanteAbonoBuilder()
                .conNumero(abono.getId())
                .conVenta(abono.getVenta().getId())
                .conFecha(abono.getFechaRegistro())
                .conMonto(abono.getMonto())
                .conMedioPago(abono.getMedioPago().getNombre())
                .conReferencia(abono.getReferenciaOperacion())
                .conObservacion(abono.getObservacion())
                .build();
    }
}
