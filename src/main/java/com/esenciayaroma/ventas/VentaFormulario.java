
package com.esenciayaroma.ventas;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class VentaFormulario {

    @NotNull(message = "Selecciona un cliente.")
    private Long clienteId;

    @NotNull(message = "Selecciona un producto.")
    private Long productoId;

    private Long pedidoClienteId;

    @NotNull(message = "Ingresa la cantidad.")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero.")
    private Integer cantidad = 1;

    @NotNull(message = "Ingresa el precio de venta.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero.")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal precioUnitario;

    @NotNull(message = "Selecciona la forma de pago.")
    private FormaPago formaPago = FormaPago.CONTADO;

    @NotNull(message = "Ingresa el pago inicial.")
    @DecimalMin(value = "0.00", message = "El pago no puede ser negativo.")
    @Digits(integer = 10, fraction = 2)
    private BigDecimal pagoInicial = BigDecimal.ZERO;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Long getProductoId() {
        return productoId;
    }

    public void setProductoId(Long productoId) {
        this.productoId = productoId;
    }

    public Long getPedidoClienteId() {
        return pedidoClienteId;
    }

    public void setPedidoClienteId(Long pedidoClienteId) {
        this.pedidoClienteId = pedidoClienteId;
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

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public BigDecimal getPagoInicial() {
        return pagoInicial;
    }

    public void setPagoInicial(BigDecimal pagoInicial) {
        this.pagoInicial = pagoInicial;
    }
}
