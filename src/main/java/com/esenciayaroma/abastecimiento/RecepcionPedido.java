
package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.Marca;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "recepciones_pedido")
public class RecepcionPedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Marca marca;

    @Column(nullable = false, length = 30)
    private String campania;

    @Column(nullable = false)
    private LocalDate fechaRecepcion;

    @OneToMany(
            mappedBy = "recepcion",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<DetalleRecepcion> detalles =
            new ArrayList<>();

    public RecepcionPedido() {
    }

    public RecepcionPedido(
            Marca marca,
            String campania,
            LocalDate fechaRecepcion) {

        this.marca = marca;
        this.campania = campania;
        this.fechaRecepcion = fechaRecepcion;
    }

    public void agregarDetalle(DetalleRecepcion detalle) {
        detalle.setRecepcion(this);
        detalles.add(detalle);
    }

    public Long getId() {
        return id;
    }

    public Marca getMarca() {
        return marca;
    }

    public String getCampania() {
        return campania;
    }

    public LocalDate getFechaRecepcion() {
        return fechaRecepcion;
    }

    public List<DetalleRecepcion> getDetalles() {
        return detalles;
    }

    public int getTotalSolicitado() {
        return detalles.stream()
                .mapToInt(DetalleRecepcion::getCantidadSolicitada)
                .sum();
    }

    public int getTotalRecibido() {
        return detalles.stream()
                .mapToInt(DetalleRecepcion::getCantidadRecibida)
                .sum();
    }

    public int getTotalFaltante() {
        return getTotalSolicitado() - getTotalRecibido();
    }
}
