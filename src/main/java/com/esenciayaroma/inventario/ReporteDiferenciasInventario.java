
package com.esenciayaroma.inventario;

import java.time.LocalDateTime;
import java.util.List;

public class ReporteDiferenciasInventario {

    private final Long conteoId;
    private final String usuario;
    private final String modalidad;
    private final String estado;
    private final LocalDateTime fechaConteo;

    private final long totalProductos;
    private final long productosFaltantes;
    private final long productosSobrantes;
    private final long productosCoincidentes;
    private final long unidadesFaltantes;
    private final long unidadesSobrantes;

    private final List<LineaDiferenciaInventario> lineas;

    public ReporteDiferenciasInventario(
            ConteoInventario conteo,
            List<LineaDiferenciaInventario> lineas) {

        this.conteoId = conteo.getId();
        this.usuario = conteo.getUsuario();
        this.modalidad = conteo.getTipoConteo();
        this.estado = conteo.getEstado();
        this.fechaConteo = conteo.getFechaRegistro();
        this.lineas = List.copyOf(lineas);

        this.totalProductos = lineas.size();

        this.productosFaltantes = lineas.stream()
                .filter(l -> l.getDiferencia() < 0)
                .count();

        this.productosSobrantes = lineas.stream()
                .filter(l -> l.getDiferencia() > 0)
                .count();

        this.productosCoincidentes = lineas.stream()
                .filter(l -> l.getDiferencia() == 0)
                .count();

        this.unidadesFaltantes = lineas.stream()
                .filter(l -> l.getDiferencia() < 0)
                .mapToLong(l -> -(long) l.getDiferencia())
                .sum();

        this.unidadesSobrantes = lineas.stream()
                .filter(l -> l.getDiferencia() > 0)
                .mapToLong(LineaDiferenciaInventario::getDiferencia)
                .sum();
    }

    public Long getConteoId() {
        return conteoId;
    }

    public String getUsuario() {
        return usuario;
    }

    public String getModalidad() {
        return modalidad;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaConteo() {
        return fechaConteo;
    }

    public long getTotalProductos() {
        return totalProductos;
    }

    public long getProductosFaltantes() {
        return productosFaltantes;
    }

    public long getProductosSobrantes() {
        return productosSobrantes;
    }

    public long getProductosCoincidentes() {
        return productosCoincidentes;
    }

    public long getUnidadesFaltantes() {
        return unidadesFaltantes;
    }

    public long getUnidadesSobrantes() {
        return unidadesSobrantes;
    }

    public List<LineaDiferenciaInventario> getLineas() {
        return lineas;
    }
}
