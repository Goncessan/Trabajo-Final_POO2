
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.Venta;
import com.esenciayaroma.ventas.VentaRepository;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class RecordatorioCobranzaService {

    private final VentaRepository ventaRepository;
    private final VencimientoCobranzaRepository vencimientoRepository;
    private final VencimientoCobranzaFactory factory;

    public RecordatorioCobranzaService(
            VentaRepository ventaRepository,
            VencimientoCobranzaRepository vencimientoRepository,
            VencimientoCobranzaFactory factory) {

        this.ventaRepository = ventaRepository;
        this.vencimientoRepository = vencimientoRepository;
        this.factory = factory;
    }

    @Transactional
    public void registrar(VencimientoFormulario formulario) {

        Venta venta = ventaRepository.findById(formulario.getVentaId())
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "No se encontró la venta seleccionada."
                        )
                );

        VencimientoCobranza existente =
                vencimientoRepository
                        .findByVentaId(venta.getId())
                        .orElse(null);

        if (existente != null) {

            if (venta.getFormaPago() == null
                    || venta.getFormaPago()
                    != com.esenciayaroma.ventas.FormaPago.CREDITO
                    || venta.getSaldo() == null
                    || venta.getSaldo().compareTo(BigDecimal.ZERO) <= 0) {

                throw new IllegalArgumentException(
                        "La venta ya no tiene una deuda pendiente."
                );
            }

            existente.setFechaVencimiento(
                    formulario.getFechaVencimiento()
            );

            existente.setObservacion(
                    formulario.getObservacion() == null
                            ? ""
                            : formulario.getObservacion().trim()
            );

            vencimientoRepository.save(existente);
            return;
        }

        vencimientoRepository.save(
                factory.crear(venta, formulario)
        );
    }

    @Transactional(readOnly = true)
    public List<AlertaVencimiento> listarAlertas() {

        LocalDate hoy = LocalDate.now();

        return vencimientoRepository
                .findAllByOrderByFechaVencimientoAsc()
                .stream()
                .filter(v ->
                        v.getVenta().getSaldo() != null
                                && v.getVenta().getSaldo()
                                .compareTo(BigDecimal.ZERO) > 0
                )
                .map(v -> {

                    LocalDate fecha = v.getFechaVencimiento();

                    String estado;

                    if (fecha.isBefore(hoy)) {
                        estado = "VENCIDO";
                    } else if (fecha.isEqual(hoy)) {
                        estado = "VENCE HOY";
                    } else {
                        estado = "PRÓXIMO";
                    }

                    String cliente =
                            v.getVenta().getCliente() == null
                                    ? "Cliente no identificado"
                                    : v.getVenta().getCliente().getNombre();

                    return new AlertaVencimiento(
                            v.getVenta().getId(),
                            cliente,
                            fecha,
                            v.getVenta().getSaldo(),
                            estado,
                            v.getObservacion()
                    );
                })
                .toList();
    }

    @Transactional(readOnly = true)
    public ResumenVencimientos generarResumen() {

        List<AlertaVencimiento> alertas = listarAlertas();

        long vencidos = alertas.stream()
                .filter(a -> a.getEstado().equals("VENCIDO"))
                .count();

        long hoy = alertas.stream()
                .filter(a -> a.getEstado().equals("VENCE HOY"))
                .count();

        long proximos = alertas.stream()
                .filter(a -> a.getEstado().equals("PRÓXIMO"))
                .count();

        return new ResumenVencimientos(
                vencidos,
                hoy,
                proximos,
                alertas.size()
        );
    }
}
