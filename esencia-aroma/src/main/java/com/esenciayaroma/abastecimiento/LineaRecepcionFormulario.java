
package com.esenciayaroma.abastecimiento;

import jakarta.validation.constraints.*;

public class LineaRecepcionFormulario {

    @NotNull(message = "Selecciona un producto.")
    private Long productoId;

    @NotBlank(message = "Ingresa el código del catálogo.")
    @Size(max = 50, message = "El código es demasiado largo.")
    private String codigoCatalogo;

    @NotNull(message = "Indica la cantidad solicitada.")
    @Min(value = 1, message = "Lo solicitado debe ser mayor que cero.")
    private Integer cantidadSolicitada;

    @NotNull(message = "Indica la cantidad recibida.")
    @Min(value = 0, message = "Lo recibido no puede ser negativo.")
    private Integer cantidadRecibida;

    public LineaRecepcionFormulario() {
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public String getCodigoCatalogo() {
        return codigoCatalogo;
    }

    public void setCodigoCatalogo(String codigoCatalogo) {
        this.codigoCatalogo = codigoCatalogo;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public void setCantidadRecibida(Integer cantidadRecibida) {
        this.cantidadRecibida = cantidadRecibida;
    }
}
