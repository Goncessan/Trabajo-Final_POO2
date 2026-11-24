
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Service;

@Service
public class ExportacionCobranzaFacade {

    private final ReporteCobranzaService reporteService;
    private final ExportadorCobranza exportador;

    public ExportacionCobranzaFacade(
            ReporteCobranzaService reporteService,
            ExportadorCobranza exportador) {

        this.reporteService = reporteService;
        this.exportador = exportador;
    }

    public byte[] exportarCsv(
            FiltroReporteCobranza filtro) {

        ReporteCobranza reporte =
                reporteService.generar(filtro);

        return exportador.exportar(reporte);
    }
}
