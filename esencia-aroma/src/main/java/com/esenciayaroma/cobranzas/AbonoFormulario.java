
package com.esenciayaroma.cobranzas;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class AbonoFormulario {

    @NotNull(message = "Ingresa el importe del abono.")
    @DecimalMin(
            value = "0.01",
            message = "El abono debe ser mayor que cero."
    )
    @Digits(integer = 10, fraction = 2)
    private BigDecimal monto;

    @Size(max = 200)
    private String observacion;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
