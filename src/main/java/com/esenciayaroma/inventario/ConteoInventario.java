
package com.esenciayaroma.inventario;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "conteos_inventario")
public class ConteoInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String tipoConteo;

    @Column(nullable = false)
    private String estado;

    private Integer totalProductos;

    public ConteoInventario() {
    }

    public ConteoInventario(
            String usuario,
            String tipoConteo,
            Integer totalProductos) {

        this.fechaRegistro = LocalDateTime.now();
        this.usuario = usuario;
        this.tipoConteo = tipoConteo;
        this.estado = "PENDIENTE";
        this.totalProductos = totalProductos;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getTipoConteo() {
        return tipoConteo;
    }

    public String getEstado() {
        return estado;
    }

    public Integer getTotalProductos() {
        return totalProductos;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}
