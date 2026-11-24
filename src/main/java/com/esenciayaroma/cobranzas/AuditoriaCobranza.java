
package com.esenciayaroma.cobranzas;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_cobranzas")
public class AuditoriaCobranza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String accion;

    private Long ventaId;
    private Long abonoId;

    @Column(precision = 12, scale = 2)
    private BigDecimal monto;

    @Enumerated(EnumType.STRING)
    @Column(length = 30)
    private MedioPago medioPago;

    @Column(length = 100)
    private String referencia;

    public AuditoriaCobranza() {
    }

    public AuditoriaCobranza(
            String usuario,
            String accion,
            Long ventaId,
            Long abonoId,
            BigDecimal monto,
            MedioPago medioPago,
            String referencia) {

        this.fechaRegistro = LocalDateTime.now();
        this.usuario = usuario;
        this.accion = accion;
        this.ventaId = ventaId;
        this.abonoId = abonoId;
        this.monto = monto;
        this.medioPago = medioPago;
        this.referencia = referencia;
    }

    public Long getId() { return id; }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getUsuario() { return usuario; }

    public String getAccion() { return accion; }

    public Long getVentaId() { return ventaId; }

    public Long getAbonoId() { return abonoId; }

    public BigDecimal getMonto() { return monto; }

    public MedioPago getMedioPago() { return medioPago; }

    public String getReferencia() { return referencia; }
}
