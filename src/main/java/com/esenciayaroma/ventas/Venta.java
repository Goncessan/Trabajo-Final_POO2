
package com.esenciayaroma.ventas;

import com.esenciayaroma.inventario.Producto;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_cliente_id")
    private PedidoCliente pedidoCliente;

    @Column(nullable = false)
    private Integer cantidad;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal precioUnitario;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FormaPago formaPago;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal pagoInicial;

    @Column(nullable = false, precision = 12, scale = 2)
    private BigDecimal montoPagado;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public Venta() {
    }

    public Venta(
            Cliente cliente,
            Producto producto,
            PedidoCliente pedidoCliente,
            int cantidad,
            BigDecimal precioUnitario,
            FormaPago formaPago,
            BigDecimal pagoInicial) {

        this.cliente = cliente;
        this.producto = producto;
        this.pedidoCliente = pedidoCliente;
        this.cantidad = cantidad;

        this.precioUnitario = precioUnitario.setScale(
                2, RoundingMode.HALF_UP
        );

        this.total = this.precioUnitario
                .multiply(BigDecimal.valueOf(cantidad))
                .setScale(2, RoundingMode.HALF_UP);

        this.formaPago = formaPago;

        this.pagoInicial = pagoInicial.setScale(
                2, RoundingMode.HALF_UP
        );

        this.montoPagado = this.pagoInicial;
        this.fechaRegistro = LocalDateTime.now();
    }

    public BigDecimal getSaldo() {
        return total.subtract(montoPagado);
    }

    public String getEstadoPago() {
        return getSaldo().compareTo(BigDecimal.ZERO) == 0
                ? "PAGADO"
                : "PENDIENTE";
    }

    public void registrarAbono(BigDecimal monto) {

        if (monto == null
                || monto.compareTo(BigDecimal.ZERO) <= 0
                || monto.compareTo(getSaldo()) > 0) {

            throw new IllegalArgumentException(
                    "El abono debe ser mayor que cero "
                            + "y no puede superar el saldo pendiente."
            );
        }

        montoPagado = montoPagado.add(monto);
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Producto getProducto() {
        return producto;
    }

    public PedidoCliente getPedidoCliente() {
        return pedidoCliente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public BigDecimal getPrecioUnitario() {
        return precioUnitario;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public BigDecimal getPagoInicial() {
        return pagoInicial;
    }

    public BigDecimal getMontoPagado() {
        return montoPagado;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
