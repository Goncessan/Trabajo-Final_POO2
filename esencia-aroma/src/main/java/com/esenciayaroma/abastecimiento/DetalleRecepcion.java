
package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.Producto;
import jakarta.persistence.*;

@Entity
@Table(name = "detalles_recepcion")
public class DetalleRecepcion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "recepcion_id", nullable = false)
    private RecepcionPedido recepcion;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    private Producto producto;

    @Column(nullable = false, length = 50)
    private String codigoCatalogo;

    @Column(nullable = false)
    private Integer cantidadSolicitada;

    @Column(nullable = false)
    private Integer cantidadRecibida;

    public DetalleRecepcion() {
    }

    public DetalleRecepcion(
            Producto producto,
            String codigoCatalogo,
            Integer cantidadSolicitada,
            Integer cantidadRecibida) {

        this.producto = producto;
        this.codigoCatalogo = codigoCatalogo;
        this.cantidadSolicitada = cantidadSolicitada;
        this.cantidadRecibida = cantidadRecibida;
    }

    public void setRecepcion(RecepcionPedido recepcion) {
        this.recepcion = recepcion;
    }

    public Long getId() {
        return id;
    }

    public RecepcionPedido getRecepcion() {
        return recepcion;
    }

    public Producto getProducto() {
        return producto;
    }

    public String getCodigoCatalogo() {
        return codigoCatalogo;
    }

    public Integer getCantidadSolicitada() {
        return cantidadSolicitada;
    }

    public Integer getCantidadRecibida() {
        return cantidadRecibida;
    }

    public int getCantidadFaltante() {
        return cantidadSolicitada - cantidadRecibida;
    }
}
