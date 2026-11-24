
package com.esenciayaroma.inventario;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "alertas_stock")
public class AlertaStock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long productoId;

    @Column(nullable = false)
    private String codigoProducto;

    @Column(nullable = false)
    private String nombreProducto;

    private Integer stockAnterior;

    private Integer stockActual;

    private Integer stockMinimo;

    private LocalDateTime fecha;

    protected AlertaStock() {
    }

    public AlertaStock(
            EventoCambioStock evento) {

        this.productoId = evento.getProductoId();
        this.codigoProducto = evento.getCodigo();
        this.nombreProducto = evento.getNombre();
        this.stockAnterior = evento.getStockAnterior();
        this.stockActual = evento.getStockPosterior();
        this.stockMinimo = evento.getStockMinimo();
        this.fecha = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public Long getProductoId() {
        return productoId;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Integer getStockAnterior() {
        return stockAnterior;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public String getFechaFormateada() {
        return fecha == null ? "" :
                fecha.format(
                        DateTimeFormatter.ofPattern(
                                "dd/MM/yyyy HH:mm"
                        )
                );
    }
}
