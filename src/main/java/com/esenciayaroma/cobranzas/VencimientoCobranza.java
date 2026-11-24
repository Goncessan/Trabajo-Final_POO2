
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.Venta;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "vencimientos_cobranza")
public class VencimientoCobranza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id", nullable = false, unique = true)
    private Venta venta;

    @Column(nullable = false)
    private LocalDate fechaVencimiento;

    @Column(length = 250)
    private String observacion;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public VencimientoCobranza() {
    }

    public VencimientoCobranza(
            Venta venta,
            LocalDate fechaVencimiento,
            String observacion) {

        this.venta = venta;
        this.fechaVencimiento = fechaVencimiento;
        this.observacion = observacion;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Venta getVenta() {
        return venta;
    }

    public LocalDate getFechaVencimiento() {
        return fechaVencimiento;
    }

    public String getObservacion() {
        return observacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaVencimiento(LocalDate fecha) {
        this.fechaVencimiento = fecha;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }
}
