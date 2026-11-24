
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
    private final AbonoFactory abonoFactory;
    private final AuditoriaCobranzaService auditoriaService;

    public CobranzaService(
            VentaRepository ventaRepository,
            AbonoRepository abonoRepository,
            AbonoFactory abonoFactory,
            AuditoriaCobranzaService auditoriaService) {

        this.ventaRepository = ventaRepository;
        this.abonoRepository = abonoRepository;
        this.abonoFactory = abonoFactory;
        this.auditoriaService = auditoriaService;
    }

    // LISTAR VENTAS AL CRÉDITO

    @Transactional(readOnly = true)
    public List<Venta> listarCreditos() {

        return ventaRepository
                .findAllByFormaPagoOrderByFechaRegistroDescIdDesc(
                        FormaPago.CREDITO
                );
    }

    // LISTAR TODAS LAS VENTAS

    @Transactional(readOnly = true)
    public List<Venta> listarTodasVentas() {

        return ventaRepository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    // OBTENER VENTA

    @Transactional(readOnly = true)
    public Venta obtenerVenta(Long id) {

        return ventaRepository.buscarDetalle(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Venta no encontrada"
                        )
                );
    }

    // LISTAR ABONOS DE UNA VENTA

    @Transactional(readOnly = true)
    public List<Abono> listarAbonos(Long ventaId) {

        return abonoRepository
                .findAllByVentaIdOrderByFechaRegistroDescIdDesc(
                        ventaId
                );
    }

    // REGISTRAR ABONO Y AUDITORÍA

    @Transactional
    public void registrarAbono(
            Long ventaId,
            AbonoFormulario formulario) {

        // 1. Buscar y bloquear la venta

        Venta venta = ventaRepository
                .buscarParaAbono(ventaId)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Venta no encontrada"
                        )
                );

        // 2. Validar que sea venta al crédito

        if (venta.getFormaPago() != FormaPago.CREDITO) {

            throw new IllegalArgumentException(
                    "Solo se pueden registrar abonos "
                            + "en ventas al crédito."
            );
        }

        // 3. Crear abono mediante Factory

        Abono abono = abonoFactory.crearAbono(
                venta,
                formulario
        );

        // 4. Validar y actualizar saldo

        venta.registrarAbono(abono.getMonto());

        // 5. Guardar la venta y el abono

        ventaRepository.save(venta);

        Abono abonoGuardado =
                abonoRepository.saveAndFlush(abono);

        // 6. Registrar auditoría en la misma transacción

        auditoriaService.registrarAbono(abonoGuardado);
    }
}
