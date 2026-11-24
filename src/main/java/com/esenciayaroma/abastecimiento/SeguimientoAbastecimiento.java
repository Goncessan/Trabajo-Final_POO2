package com.esenciayaroma.abastecimiento;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "seguimientos_abastecimiento")
public class SeguimientoAbastecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String observacion;

    private LocalDate fechaSeguimiento;

    @Enumerated(EnumType.STRING)
    private EstadoSeguimiento estado;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoAbastecimiento pedido;

    public SeguimientoAbastecimiento() {
    }

    public Long getId() {
        return id;
    }

    public String getObservacion() {
        return observacion;
    }

    public void setObservacion(String observacion) {
        this.observacion = observacion;
    }

    public LocalDate getFechaSeguimiento() {
        return fechaSeguimiento;
    }

    public void setFechaSeguimiento(LocalDate fechaSeguimiento) {
        this.fechaSeguimiento = fechaSeguimiento;
    }

    public EstadoSeguimiento getEstado() {
        return estado;
    }

    public void setEstado(EstadoSeguimiento estado) {
        this.estado = estado;
    }

    public PedidoAbastecimiento getPedido() {
        return pedido;
    }

    public void setPedido(PedidoAbastecimiento pedido) {
        this.pedido = pedido;
    }
}
