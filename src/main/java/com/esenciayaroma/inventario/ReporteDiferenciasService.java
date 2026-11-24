
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReporteDiferenciasService {

    private final ConteoInventarioService conteoService;

    public ReporteDiferenciasService(
            ConteoInventarioService conteoService) {

        this.conteoService = conteoService;
    }

    @Transactional(readOnly = true)
    public ReporteDiferenciasInventario generarReporte(
            Long conteoId) {

        // 1. Obtener el conteo registrado

        ConteoInventario conteo =
                conteoService.obtenerConteo(conteoId);

        // 2. Consultar los productos revisados

        List<DetalleConteoInventario> detalles =
                conteoService.listarDetalles(conteoId);

        // 3. Convertir los detalles en líneas de reporte

        List<LineaDiferenciaInventario> lineas =
                detalles.stream()
                        .map(LineaDiferenciaInventario::new)
                        .toList();

        // 4. Generar el resumen del conteo

        return new ReporteDiferenciasInventario(
                conteo,
                lineas
        );
    }
}
