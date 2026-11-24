
package com.esenciayaroma.inventario;

import jakarta.persistence.*;

@Entity
@Table(name = "detalles_conteo_inventario")
public class DetalleConteoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conteo_id", nullable = false)
    private ConteoInventario conteo;

    private Long productoId;

    private String codigoProducto;

    private String nombreProducto;

    private Integer stockSistema;

    private Integer stockFisico;

    private Integer diferencia;

    private boolean ajusteAplicado;

    public DetalleConteoInventario() {
    }

    public DetalleConteoInventario(
            ConteoInventario conteo,
            Long productoId,
            String codigoProducto,
            String nombreProducto,
            Integer stockSistema,
            Integer stockFisico) {

        this.conteo = conteo;
        this.productoId = productoId;
        this.codigoProducto = codigoProducto;
        this.nombreProducto = nombreProducto;
        this.stockSistema = stockSistema;
        this.stockFisico = stockFisico;
        this.diferencia = stockFisico - stockSistema;
        this.ajusteAplicado = false;
    }

    public Long getId() {
        return id;
    }

    public ConteoInventario getConteo() {
        return conteo;
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

    public Integer getStockSistema() {
        return stockSistema;
    }

    public Integer getStockFisico() {
        return stockFisico;
    }

    public Integer getDiferencia() {
        return diferencia;
    }

    public boolean isAjusteAplicado() {
        return ajusteAplicado;
    }

    public void setAjusteAplicado(boolean ajusteAplicado) {
        this.ajusteAplicado = ajusteAplicado;
    }
}
