
package com.esenciayaroma.cobranzas;

import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class VencimientoFormulario {

    @NotNull(message = "Selecciona una venta.")
    private Long ventaId;

    @NotNull(message = "Selecciona la fecha de vencimiento.")
    @FutureOrPresent(message = "La fecha no puede ser anterior a hoy.")
    private LocalDate fechaVencimiento;

    @Size(max = 250, message = "La observación no puede superar 250 caracteres.")
    private String observacion;

    public Long getVentaId() {
        return ventaId;
    }

    public void setVentaId(Long ventaId) {
        this.ventaId = ventaId;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(LocalDate fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
