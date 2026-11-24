package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.Producto;
import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "detalles_pedido_abastecimiento")
public class DetallePedidoAbastecimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id")
    private PedidoAbastecimiento pedido;

    @ManyToOne
    @JoinColumn(name = "producto_id")
    private Producto producto;

    private Integer cantidad;

    private BigDecimal precioUnitario;

    public DetallePedidoAbastecimiento() {
    }

    public DetallePedidoAbastecimiento(
            Producto producto,
            Integer cantidad,
            BigDecimal precioUnitario) {

        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public Long getId() {
        return id;
    }

    public PedidoAbastecimiento getPedido() {
        return pedido;
    }

    public void setPedido(PedidoAbastecimiento pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getSubtotal() {
        return precioUnitario.multiply(
                BigDecimal.valueOf(cantidad)
        );
    }
}
