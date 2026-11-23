package com.esenciayaroma.inventario;

import jakarta.validation.constraints.*;

public class MovimientoFormulario {

    @NotNull(message = "Selecciona un producto.")
    private Long productoId;

    @NotNull(message = "Selecciona el tipo de movimiento.")
    private TipoMovimiento tipo;

    @NotNull(message = "Ingresa la cantidad.")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero.")
    private Integer cantidad;

    @NotBlank(message = "Ingresa el motivo del movimiento.")
    @Size(max = 250, message = "El motivo no puede superar 250 caracteres.")
    private String motivo;

    public MovimientoFormulario() {
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public TipoMovimiento getTipo() {
        return tipo;
    }

    public void setTipo(TipoMovimiento tipo) {
        this.tipo = tipo;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
}
