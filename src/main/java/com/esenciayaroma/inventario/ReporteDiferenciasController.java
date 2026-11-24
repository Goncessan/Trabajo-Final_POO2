
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventario/conteos")
public class ReporteDiferenciasController {

    private final ReporteDiferenciasService reporteService;

    public ReporteDiferenciasController(
            ReporteDiferenciasService reporteService) {

        this.reporteService = reporteService;
    }

    @GetMapping("/{id}/reporte")
    public String mostrarReporte(
            @PathVariable Long id,
            Model model) {

        ReporteDiferenciasInventario reporte =
                reporteService.generarReporte(id);

        model.addAttribute("reporte", reporte);

        return "reporte-diferencias-inventario";
    }
}
