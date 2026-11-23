
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.Venta;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "abonos")
public class Abono {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "venta_id", nullable = false)
    private Venta venta;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal monto;

    @Column(length = 200)
    private String observacion;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public Abono() {
    }

    public Abono(
            Venta venta,
            BigDecimal monto,
            String observacion) {

        this.venta = venta;
        this.monto = monto;
        this.observacion = observacion;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Venta getVenta() {
        return venta;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public String getObservacion() {
        return observacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
