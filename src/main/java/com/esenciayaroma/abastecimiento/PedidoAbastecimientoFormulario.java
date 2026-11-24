package com.esenciayaroma.abastecimiento;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoAbastecimientoFormulario {

    @NotNull(message = "Selecciona un proveedor.")
    private Long proveedorId;

    @NotNull(message = "Selecciona una campaña.")
    private Long campaniaId;

    @NotNull(message = "Selecciona una prioridad.")
    private PrioridadPedido prioridad;

    @Valid
    @NotEmpty(message = "Agrega al menos un producto.")
    private List<LineaPedidoAbastecimientoFormulario> detalles =
            new ArrayList<>();

    public PedidoAbastecimientoFormulario() {
        detalles.add(new LineaPedidoAbastecimientoFormulario());
    }

    public Long getProveedorId() {
        return proveedorId;
    }

    public void setProveedorId(Long proveedorId) {
        this.proveedorId = proveedorId;
    }

    public Long getCampaniaId() {
        return campaniaId;
    }

    public void setCampaniaId(Long campaniaId) {
        this.campaniaId = campaniaId;
    }

    public PrioridadPedido getPrioridad() {
        return prioridad;
    }

    public void setPrioridad(PrioridadPedido prioridad) {
        this.prioridad = prioridad;
    }

    public List<LineaPedidoAbastecimientoFormulario> getDetalles() {
        return detalles;
    }

    public void setDetalles(
            List<LineaPedidoAbastecimientoFormulario> detalles) {
        this.detalles = detalles;
    }
}
