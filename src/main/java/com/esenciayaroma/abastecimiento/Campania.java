
package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.Marca;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "campanias")
public class Campania {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;

    @Enumerated(EnumType.STRING)
    private Marca marca;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @Enumerated(EnumType.STRING)
    private EstadoCampania estado;

    public Campania() {
    }

    public Campania(String nombre, Marca marca,
                    LocalDate fechaInicio, LocalDate fechaFin,
                    EstadoCampania estado) {
        this.nombre = nombre;
        this.marca = marca;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Marca getMarca() {
        return marca;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public EstadoCampania getEstado() {
        return estado;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setEstado(EstadoCampania estado) {
        this.estado = estado;
    }
}
