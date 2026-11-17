
package com.esenciayaroma.ventas;

import com.esenciayaroma.inventario.Marca;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos_clientes")
public class PedidoCliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Marca marca;

    @Column(nullable = false, length = 50)
    private String codigoCatalogo;

    @Column(nullable = false, length = 150)
    private String nombreProducto;

    @Column(nullable = false)
    private Integer cantidadSolicitada;

    @Column(nullable = false)
    private Integer cantidadEntregada = 0;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public PedidoCliente() {
    }

    public PedidoCliente(
            Cliente cliente,
            Marca marca,
            String codigoCatalogo,
            String nombreProducto,
            Integer cantidadSolicitada) {

        this.cliente = cliente;
        this.marca = marca;
        this.codigoCatalogo = codigoCatalogo;
        this.nombreProducto = nombreProducto;
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadEntregada = 0;
        this.fechaRegistro = LocalDateTime.now();
    }

    public void registrarEntrega(int cantidad) {

        if (cantidad <= 0 || cantidad > getCantidadPendiente()) {
            throw new IllegalArgumentException(
                    "La cantidad supera lo pendiente del encargo."
            );
        }

        cantidadEntregada += cantidad;
    }

    public int getCantidadPendiente() {
        return cantidadSolicitada - cantidadEntregada;
    }

    public String getEstado() {

        if (cantidadEntregada == 0) {
            return "PENDIENTE";
        }

        if (cantidadEntregada < cantidadSolicitada) {
            return "PARCIAL";
        }

        return "ENTREGADO";
    }

    public Long getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Marca getMarca() {
        return marca;
    }

    public String getCodigoCatalogo() {
        return codigoCatalogo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public Integer getCantidadEntregada() {
        return cantidadEntregada;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
