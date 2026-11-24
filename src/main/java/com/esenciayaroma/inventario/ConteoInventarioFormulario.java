
package com.esenciayaroma.inventario;

import java.util.ArrayList;
import java.util.List;

public class ConteoInventarioFormulario {

    private String tipoConteo = "COMPLETO";

    private List<DetalleConteoFormulario> detalles =
            new ArrayList<>();

    public String getTipoConteo() {
        return tipoConteo;
    }

    public void setTipoConteo(String tipoConteo) {
        this.tipoConteo = tipoConteo;
    }

    public List<DetalleConteoFormulario> getDetalles() {
        return detalles;
    }

    public void setDetalles(
            List<DetalleConteoFormulario> detalles) {

        this.detalles = detalles;
    }
}
