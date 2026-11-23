
package com.esenciayaroma.ventas;

import com.esenciayaroma.inventario.Marca;
import jakarta.validation.constraints.*;

public class PedidoClienteFormulario {

    @NotNull(message = "Selecciona el cliente.")
    private Long clienteId;

    @NotNull(message = "Selecciona la marca.")
    private Marca marca;

    @NotBlank(message = "Ingresa el código del catálogo.")
    @Size(max = 50)
    private String codigoCatalogo;

    @NotBlank(message = "Ingresa el nombre del producto.")
    @Size(max = 150)
    private String nombreProducto;

    @NotNull(message = "Ingresa la cantidad.")
    @Min(value = 1, message = "La cantidad debe ser mayor que cero.")
    private Integer cantidadSolicitada;

    public Long getClienteId() {
        return clienteId;
    }

    public void setClienteId(Long clienteId) {
        this.clienteId = clienteId;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getCodigoCatalogo() {
        return codigoCatalogo;
    }

    public void setCodigoCatalogo(String codigoCatalogo) {
        this.codigoCatalogo = codigoCatalogo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public void setCantidadSolicitada(Integer cantidadSolicitada) {
        this.cantidadSolicitada = cantidadSolicitada;
    }
}
