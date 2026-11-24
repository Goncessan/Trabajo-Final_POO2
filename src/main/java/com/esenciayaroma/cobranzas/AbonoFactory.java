
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.Venta;
import org.springframework.stereotype.Component;

@Component
public class AbonoFactory {

    private final ValidadorPago validadorPago;

    public AbonoFactory(ValidadorPago validadorPago) {
        this.validadorPago = validadorPago;
    }

    public Abono crearAbono(
            Venta venta,
            AbonoFormulario formulario) {

        if (venta == null || formulario == null) {
            throw new IllegalArgumentException(
                    "Los datos del abono son obligatorios."
            );
        }

        validadorPago.validar(
                formulario.getMonto(),
                formulario.getMedioPago(),
                formulario.getReferenciaOperacion(),
                formulario.getObservacion()
        );

        String referencia = formulario.getReferenciaOperacion();
        String observacion = formulario.getObservacion();

        referencia = referencia == null
                ? null : referencia.trim();

        observacion = observacion == null
                ? null : observacion.trim();

        return new Abono(
                venta,
                formulario.getMonto(),
                formulario.getMedioPago(),
                referencia,
                observacion
        );
    }
}
