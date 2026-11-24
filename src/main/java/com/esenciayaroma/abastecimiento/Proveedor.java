
package com.esenciayaroma.abastecimiento;

import jakarta.persistence.*;

@Entity
@Table(name = "proveedores")
public class Proveedor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String telefono;
    private String correo;
    private String direccion;

    @Enumerated(EnumType.STRING)
    private TipoProveedor tipoProveedor;

    private boolean activo = true;

    public Proveedor() {
    }

    public Proveedor(String nombre, String telefono,
                     String correo, String direccion,
                     TipoProveedor tipoProveedor) {
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
        this.direccion = direccion;
        this.tipoProveedor = tipoProveedor;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public TipoProveedor getTipoProveedor() {
        return tipoProveedor;
    }

    public boolean isActivo() {
        return activo;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public void setTipoProveedor(TipoProveedor tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }
}
