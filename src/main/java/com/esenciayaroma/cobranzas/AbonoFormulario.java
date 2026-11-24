
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

    @NotNull(message = "Selecciona el medio de pago.")
    private MedioPago medioPago;

    @Size(
            max = 100,
            message = "La referencia no puede superar 100 caracteres."
    )
    private String referenciaOperacion;

    @Size(
            max = 200,
            message = "La observación no puede superar 200 caracteres."
    )
    private String observacion;

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public MedioPago getMedioPago() {
        return medioPago;
    }

    public void setMedioPago(MedioPago medioPago) {
        this.medioPago = medioPago;
    }

    public String getReferenciaOperacion() {
        return referenciaOperacion;
    }

    public void setReferenciaOperacion(String referenciaOperacion) {
        this.referenciaOperacion = referenciaOperacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
