
package com.esenciayaroma.inventario;

public class DetalleConteoFormulario {

    private Long productoId;

    private String codigoProducto;

    private String nombreProducto;

    private Integer stockSistema;

    private Integer stockFisico;

    private boolean seleccionado = true;

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(String codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getStockSistema() {
        return stockSistema;
    }

    public void setStockSistema(Integer stockSistema) {
        this.stockSistema = stockSistema;
    }

    public Integer getStockFisico() {
        return stockFisico;
    }

    public void setStockFisico(Integer stockFisico) {
        this.stockFisico = stockFisico;
    }

    public boolean isSeleccionado() {
        return seleccionado;
    }

    public void setSeleccionado(boolean seleccionado) {
        this.seleccionado = seleccionado;
    }
}
