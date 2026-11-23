
package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.Marca;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class RecepcionFormulario {

    @NotNull(message = "Selecciona una marca.")
    private Marca marca;

    @NotBlank(message = "Ingresa la campaña.")
    @Size(max = 30, message = "Máximo 30 caracteres.")
    private String campania;

    @NotNull(message = "Selecciona la fecha.")
    @PastOrPresent(message = "La fecha no puede ser futura.")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fechaRecepcion = LocalDate.now();

    @Valid
    @NotEmpty(message = "Agrega al menos un producto.")
    @Size(max = 30, message = "Máximo 30 productos por recepción.")
    private List<LineaRecepcionFormulario> detalles =
            new ArrayList<>();

    public RecepcionFormulario() {
        detalles.add(new LineaRecepcionFormulario());
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getCampania() {
        return campania;
    }

    public void setCampania(String campania) {
        this.campania = campania;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public void setFechaRecepcion(LocalDate fechaRecepcion) {
        this.fechaRecepcion = fechaRecepcion;
    }

    public List<LineaRecepcionFormulario> getDetalles() {
        return detalles;
    }

    public void setDetalles(
            List<LineaRecepcionFormulario> detalles) {
        this.detalles = detalles;
    }
}
