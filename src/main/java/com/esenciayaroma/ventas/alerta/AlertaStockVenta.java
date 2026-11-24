package com.esenciayaroma.ventas.alerta;

import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * Alerta generada por el Observer cuando una venta deja un producto
 * con stock bajo o agotado. Guarda una copia del id y del nombre del
 * producto para no depender de la tabla de inventario.
 */
@Entity
@Table(name = "alertas_stock_venta")
public class AlertaStockVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false, length = 150)
    private String productoNombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TipoAlertaStock tipo;

    @Column(nullable = false)
    private int stockActual;

    @Column(nullable = false)
    private int stockMinimo;

    @Column(nullable = false, length = 300)
    private String mensaje;

    @Column(nullable = false)
    private boolean atendida = false;

    @Column(nullable = false)
    private LocalDateTime fechaCreacion;

    public AlertaStockVenta() {
    }

    public AlertaStockVenta(
            Long productoId,
            String productoNombre,
            TipoAlertaStock tipo,
            int stockActual,
            int stockMinimo,
            String mensaje) {

        this.productoId = productoId;
        this.productoNombre = productoNombre;
        this.tipo = tipo;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.mensaje = mensaje;
        this.atendida = false;
        this.fechaCreacion = LocalDateTime.now();
    }

    public void marcarAtendida() {
        this.atendida = true;
    }

    public Long getId() {
        return id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getProductoNombre() {
        return productoNombre;
    }

    public TipoAlertaStock getTipo() {
        return tipo;
    }

    public int getStockActual() {
        return stockActual;
    }

    public int getStockMinimo() {
        return stockMinimo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public boolean isAtendida() {
        return atendida;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
}
