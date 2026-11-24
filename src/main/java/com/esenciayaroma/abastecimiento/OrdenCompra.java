package com.esenciayaroma.abastecimiento;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.math.BigDecimal;

@Entity
@Table(name = "ordenes_compra")
public class OrdenCompra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroOrden;

    private LocalDate fechaEmision;

    private LocalDate fechaEntrega;

    private BigDecimal montoTotal;

    @Enumerated(EnumType.STRING)
    private EstadoOrdenCompra estado;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private PedidoAbastecimiento pedido;

    public OrdenCompra() {
    }

    public OrdenCompra(String numeroOrden,
                       LocalDate fechaEmision,
                       LocalDate fechaEntrega,
                       BigDecimal montoTotal,
                       PedidoAbastecimiento pedido) {

        this.numeroOrden = numeroOrden;
        this.fechaEmision = fechaEmision;
        this.fechaEntrega = fechaEntrega;
        this.montoTotal = montoTotal;
        this.pedido = pedido;
        this.estado = EstadoOrdenCompra.EMITIDA;
    }

    public Long getId() {
        return id;
    }

    public String getNumeroOrden() {
        return numeroOrden;
    }

    public LocalDate getFechaEmision() {
        return fechaEmision;
    }

    public LocalDate getFechaEntrega() {
        return fechaEntrega;
    }

    public BigDecimal getMontoTotal() {
        return montoTotal;
    }

    public EstadoOrdenCompra getEstado() {
        return estado;
    }

    public PedidoAbastecimiento getPedido() {
        return pedido;
    }

    public void setFechaEntrega(LocalDate fechaEntrega) {
        this.fechaEntrega = fechaEntrega;
    }

    public void setEstado(EstadoOrdenCompra estado) {
        this.estado = estado;
    }
}
