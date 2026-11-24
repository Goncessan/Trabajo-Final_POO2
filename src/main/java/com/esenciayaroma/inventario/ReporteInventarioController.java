
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReporteInventarioController {

    private final ReporteInventarioService reporteService;

    public ReporteInventarioController(
            ReporteInventarioService reporteService) {

        this.reporteService = reporteService;
    }

    @GetMapping("/inventario/reporte")
    public String mostrarReporte(Model model) {

        ReporteInventario reporte =
                reporteService.generarReporte();

        model.addAttribute("reporte", reporte);

        return "reporte-inventario";
    }
}
