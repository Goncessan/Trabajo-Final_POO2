
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Locale;

@Service
public class CuentasPorCobrarService {

    private final CobranzaService cobranzaService;
    private final CuentaPorCobrarFactory factory;

    public CuentasPorCobrarService(
            CobranzaService cobranzaService,
            CuentaPorCobrarFactory factory) {

        this.cobranzaService = cobranzaService;
        this.factory = factory;
    }

    // LISTAR CUENTAS PENDIENTES

    @Transactional(readOnly = true)
    public List<CuentaPorCobrar> listarPendientes(
            String busqueda) {

        String filtro = busqueda == null
                ? ""
                : busqueda.trim().toLowerCase(Locale.ROOT);

        return cobranzaService.listarCreditos()
                .stream()

                .filter(venta ->
                        venta.getSaldo() != null
                                && venta.getSaldo()
                                .compareTo(BigDecimal.ZERO) > 0
                )

                .map(factory::crear)

                .filter(cuenta ->
                        filtro.isEmpty()
                                || cuenta.getCliente()
                                .toLowerCase(Locale.ROOT)
                                .contains(filtro)
                                || String.valueOf(cuenta.getVentaId())
                                .contains(filtro)
                )

                .toList();
    }

    // GENERAR RESUMEN DE CUENTAS POR COBRAR

    @Transactional(readOnly = true)
    public ResumenCuentasPorCobrar generarResumen() {

        List<CuentaPorCobrar> cuentas =
                listarPendientes("");

        long totalVentas = cuentas.size();

        long ventasConAbonos = cuentas.stream()
                .filter(cuenta ->
                        cuenta.getMontoPagado()
                                .compareTo(BigDecimal.ZERO) > 0
                )
                .count();

        long clientes = cuentas.stream()
                .map(CuentaPorCobrar::getCliente)
                .distinct()
                .count();

        BigDecimal saldoTotal = cuentas.stream()
                .map(CuentaPorCobrar::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new ResumenCuentasPorCobrar(
                totalVentas,
                ventasConAbonos,
                clientes,
                saldoTotal
        );
    }
}
