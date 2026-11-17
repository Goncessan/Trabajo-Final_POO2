
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CobranzaService {

    private final VentaRepository ventaRepository;
    private final AbonoRepository abonoRepository;

    public CobranzaService(
            VentaRepository ventaRepository,
            AbonoRepository abonoRepository) {

        this.ventaRepository = ventaRepository;
        this.abonoRepository = abonoRepository;
    }

    // CONSULTAR VENTAS AL CREDITO
    @Transactional(readOnly = true)
    public List<Venta> listarCreditos() {

        return ventaRepository
                .findAllByFormaPagoOrderByFechaRegistroDescIdDesc(
                        FormaPago.CREDITO
                );
    }

    // CONSULTAR TODAS LAS VENTAS PARA REPORTES
    @Transactional(readOnly = true)
    public List<Venta> listarTodasVentas() {

        return ventaRepository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    // CONSULTAR UNA VENTA
    @Transactional(readOnly = true)
    public Venta obtenerVenta(Long id) {

        return ventaRepository.buscarDetalle(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Venta no encontrada"
                ));
    }

    // HISTORIAL DE ABONOS DE UNA VENTA
    @Transactional(readOnly = true)
    public List<Abono> listarAbonos(Long ventaId) {

        return abonoRepository
                .findAllByVentaIdOrderByFechaRegistroDescIdDesc(
                        ventaId
                );
    }

    // REGISTRAR ABONO
    @Transactional
    public void registrarAbono(
            Long ventaId,
            AbonoFormulario formulario) {

        Venta venta = ventaRepository.buscarParaAbono(ventaId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Venta no encontrada"
                ));

        if (venta.getFormaPago() != FormaPago.CREDITO) {
            throw new IllegalArgumentException(
                    "Solo se pueden registrar abonos " +
                            "en ventas al crédito."
            );
        }

        // Actualizar el monto pagado
        venta.registrarAbono(formulario.getMonto());

        // Crear registro del abono
        String observacion = formulario.getObservacion();

        if (observacion != null) {
            observacion = observacion.trim();
        }

        Abono abono = new Abono(
                venta,
                formulario.getMonto(),
                observacion
        );

        ventaRepository.save(venta);
        abonoRepository.save(abono);
    }
}
