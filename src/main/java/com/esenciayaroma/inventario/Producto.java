
package com.esenciayaroma.inventario;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Ingresa el código del producto.")
    @Size(max = 35)
    @Column(nullable = false, unique = true, length = 35)
    private String codigo;

    @NotBlank(message = "Ingresa el nombre del producto.")
    @Size(max = 120)
    @Column(nullable = false, length = 120)
    private String nombre;

    @NotNull(message = "Selecciona una marca.")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Marca marca;

    @NotBlank(message = "Ingresa la categoría.")
    @Size(max = 60)
    @Column(nullable = false, length = 60)
    private String categoria;

    @NotNull(message = "Ingresa el precio de compra.")
    @DecimalMin(value = "0.00", message = "El precio no puede ser negativo.")
    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal precioCompra;

    @NotNull(message = "Ingresa el precio de venta.")
    @DecimalMin(value = "0.01", message = "El precio debe ser mayor que cero.")
    @Digits(integer = 9, fraction = 2)
    @Column(nullable = false, precision = 11, scale = 2)
    private BigDecimal precioVenta;

    @NotNull(message = "Ingresa la cantidad disponible.")
    @Min(value = 0, message = "El stock no puede ser negativo.")
    @Column(nullable = false)
    private Integer stock;

    @NotNull(message = "Ingresa el stock mínimo.")
    @Min(value = 0, message = "El stock mínimo no puede ser negativo.")
    @Column(nullable = false)
    private Integer stockMinimo;

    public Producto() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getPrecioCompra() {
        return precioCompra;
    }

    public void setPrecioCompra(BigDecimal precioCompra) {
        this.precioCompra = precioCompra;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMinimo() {
        return stockMinimo;
    }

    public void setStockMinimo(Integer stockMinimo) {
        this.stockMinimo = stockMinimo;
    }
}
