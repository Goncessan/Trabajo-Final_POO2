
package com.esenciayaroma.cobranzas;

import java.math.BigDecimal;

public class ResumenMedioPago {

    private final MedioPago medioPago;
    private final long cantidadOperaciones;
    private final BigDecimal importeTotal;

    public ResumenMedioPago(
            MedioPago medioPago,
            long cantidadOperaciones,
            BigDecimal importeTotal) {

        this.medioPago = medioPago;
        this.cantidadOperaciones = cantidadOperaciones;
        this.importeTotal = importeTotal;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public String getNombre() {
        return medioPago.getNombre();
    }

    public long getCantidadOperaciones() {
        return cantidadOperaciones;
    }

    public BigDecimal getImporteTotal() {
        return importeTotal;
    }
}
