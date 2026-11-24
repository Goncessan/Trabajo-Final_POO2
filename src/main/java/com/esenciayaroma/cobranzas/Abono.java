
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

    @Enumerated(EnumType.STRING)
    @Column(name = "medio_pago", length = 30)
    private MedioPago medioPago;

    @Column(name = "referencia_operacion", length = 100)
    private String referenciaOperacion;

    @Column(length = 200)
    private String observacion;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public Abono() {
    }

    // Constructor original: compatibilidad
    public Abono(
            Venta venta,
            BigDecimal monto,
            String observacion) {

        this(
                venta,
                monto,
                MedioPago.NO_ESPECIFICADO,
                null,
                observacion
        );
    }

    // Constructor con información del medio de pago
    public Abono(
            Venta venta,
            BigDecimal monto,
            MedioPago medioPago,
            String referenciaOperacion,
            String observacion) {

        this.venta = venta;
        this.monto = monto;
        this.medioPago = medioPago;
        this.referenciaOperacion = referenciaOperacion;
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

    public MedioPago getMedioPago() {
        return medioPago == null
                ? MedioPago.NO_ESPECIFICADO
                : medioPago;
    }

    public String getReferenciaOperacion() {
        return referenciaOperacion;
    }

    public String getObservacion() {
        return observacion;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
