package com.esenciayaroma.inventario;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "movimientos_inventario")
public class MovimientoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private TipoMovimiento tipo;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false)
    private Integer stockAnterior;

    @Column(nullable = false)
    private Integer stockPosterior;

    @Column(nullable = false, length = 250)
    private String motivo;

    @Column(nullable = false, updatable = false)
    private LocalDateTime fecha;

    public MovimientoInventario() {
    }

    public MovimientoInventario(
            Producto producto,
            TipoMovimiento tipo,
            Integer cantidad,
            Integer stockAnterior,
            Integer stockPosterior,
            String motivo) {

        this.producto = producto;
        this.tipo = tipo;
        this.cantidad = cantidad;
        this.stockAnterior = stockAnterior;
        this.stockPosterior = stockPosterior;
        this.motivo = motivo;
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Producto getProducto() {
        return producto;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public Integer getStockAnterior() {
        return stockAnterior;
    }

    public Integer getStockPosterior() {
        return stockPosterior;
    }

    public String getMotivo() {
        return motivo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }
}
