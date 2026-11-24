package com.esenciayaroma.abastecimiento;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class LineaPedidoAbastecimientoFormulario {

    @NotNull(message = "Selecciona un producto.")
    private Long productoId;

    @NotNull(message = "Ingresa la cantidad.")
    @Min(value = 1, message = "La cantidad debe ser mayor a cero.")
    private Integer cantidad;

    @NotNull(message = "Ingresa el precio.")
    @DecimalMin(value = "0.01")
    private BigDecimal precioUnitario;

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(BigDecimal precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
}
