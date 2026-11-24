package com.esenciayaroma.abastecimiento;

import jakarta.validation.constraints.*;

public class ProveedorFormulario {

    @NotBlank(message = "Ingresa el nombre.")
    private String nombre;

    @NotBlank(message = "Ingresa el teléfono.")
    private String telefono;

    @NotBlank(message = "Ingresa el correo.")
    @Email(message = "El correo no es válido.")
    private String correo;

    private String direccion;

    @NotNull(message = "Selecciona el tipo de proveedor.")
    private TipoProveedor tipoProveedor;

    public ProveedorFormulario() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public TipoProveedor getTipoProveedor() {
        return tipoProveedor;
    }

    public void setTipoProveedor(TipoProveedor tipoProveedor) {
        this.tipoProveedor = tipoProveedor;
    }
}
