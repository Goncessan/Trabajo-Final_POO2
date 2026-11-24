package com.esenciayaroma.abastecimiento;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pedidos_abastecimiento")
public class PedidoAbastecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaPedido;

    @ManyToOne
    @JoinColumn(name = "proveedor_id")
    private Proveedor proveedor;

    @ManyToOne
    @JoinColumn(name = "campania_id")
    private Campania campania;

    @Enumerated(EnumType.STRING)
    private EstadoPedidoAbastecimiento estado;

    @Enumerated(EnumType.STRING)
    private PrioridadPedido prioridad;

    @OneToMany(
            mappedBy = "pedido",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetallePedidoAbastecimiento> detalles =
            new ArrayList<>();

    public PedidoAbastecimiento() {
    }

    public PedidoAbastecimiento(
            LocalDate fechaPedido,
            Proveedor proveedor,
            Campania campania,
            PrioridadPedido prioridad) {

        this.fechaPedido = fechaPedido;
        this.proveedor = proveedor;
        this.campania = campania;
        this.prioridad = prioridad;
        this.estado = EstadoPedidoAbastecimiento.PENDIENTE;
    }

    public void agregarDetalle(
            DetallePedidoAbastecimiento detalle) {
        detalle.setPedido(this);
        detalles.add(detalle);
    }

    public Long getId() {
        return id;
    }

    public LocalDate getFechaPedido() {
        return fechaPedido;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public Campania getCampania() {
        return campania;
    }

    public EstadoPedidoAbastecimiento getEstado() {
        return estado;
    }

    public PrioridadPedido getPrioridad() {
        return prioridad;
    }

    public List<DetallePedidoAbastecimiento> getDetalles() {
        return detalles;
    }

    public void setEstado(EstadoPedidoAbastecimiento estado) {
        this.estado = estado;
    }

    public void setPrioridad(PrioridadPedido prioridad) {
        this.prioridad = prioridad;
    }
}
