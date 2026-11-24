
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;

public class ResumenCuentasPorCobrar {

    private final long totalVentasPendientes;
    private final long ventasConAbonos;
    private final long clientesConDeuda;
    private final BigDecimal saldoTotal;

    public ResumenCuentasPorCobrar(
            long totalVentasPendientes,
            long ventasConAbonos,
            long clientesConDeuda,
            BigDecimal saldoTotal) {

        this.totalVentasPendientes = totalVentasPendientes;
        this.ventasConAbonos = ventasConAbonos;
        this.clientesConDeuda = clientesConDeuda;
        this.saldoTotal = saldoTotal;
    }

    public long getTotalVentasPendientes() {
        return totalVentasPendientes;
    }

    public long getVentasConAbonos() {
        return ventasConAbonos;
    }

    public long getClientesConDeuda() {
        return clientesConDeuda;
    }

    public BigDecimal getSaldoTotal() {
        return saldoTotal;
    }
}
