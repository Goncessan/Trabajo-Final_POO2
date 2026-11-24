
package com.esenciayaroma.inventario;

public class LineaDiferenciaInventario {

    private final String codigo;
    private final String producto;
    private final int stockSistema;
    private final int stockFisico;
    private final int diferencia;
    private final String resultado;
    private final boolean ajusteAplicado;

    public LineaDiferenciaInventario(
            DetalleConteoInventario detalle) {

        this.codigo = detalle.getCodigoProducto();
        this.producto = detalle.getNombreProducto();
        this.stockSistema = detalle.getStockSistema();
        this.stockFisico = detalle.getStockFisico();
        this.diferencia = detalle.getDiferencia();
        this.ajusteAplicado = detalle.isAjusteAplicado();

        if (diferencia > 0) {
            this.resultado = "Sobrante";
        } else if (diferencia < 0) {
            this.resultado = "Faltante";
        } else {
            this.resultado = "Coincide";
        }
    }

    public String getCodigo() {
        return codigo;
    }

    public String getProducto() {
        return producto;
    }

    public int getStockSistema() {
        return stockSistema;
    }

    public int getStockFisico() {
        return stockFisico;
    }

    public int getDiferencia() {
        return diferencia;
    }

    public String getResultado() {
        return resultado;
    }

    public boolean isAjusteAplicado() {
        return ajusteAplicado;
    }
}
