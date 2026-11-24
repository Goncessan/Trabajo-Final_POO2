
package com.esenciayaroma.inventario;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "auditoria_inventario")
public class AuditoriaInventario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String usuario;

    @Column(nullable = false)
    private String accion;

    @Column(nullable = false)
    private String modulo;

    @Column(length = 500)
    private String descripcion;

    private String codigoProducto;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    public AuditoriaInventario() {
    }

    public AuditoriaInventario(
            String usuario,
            String accion,
            String modulo,
            String descripcion,
            String codigoProducto) {

        this.usuario = usuario;
        this.accion = accion;
        this.modulo = modulo;
        this.descripcion = descripcion;
        this.codigoProducto = codigoProducto;
        this.fechaRegistro = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getAccion() {
        return accion;
    }

    public String getModulo() {
        return modulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getCodigoProducto() {
        return codigoProducto;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }
}
