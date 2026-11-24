
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.math.BigDecimal;

@Controller
@RequestMapping("/cobranzas/medios-pago")
public class ReporteMediosPagoController {

    private final ReporteMediosPagoService reporteService;

    public ReporteMediosPagoController(
            ReporteMediosPagoService reporteService) {

        this.reporteService = reporteService;
    }

    @GetMapping
    public String mostrarReporte(Model model) {

        var resumen = reporteService.generarResumen();
        var pagos = reporteService.listarPagos();

        BigDecimal total = resumen.stream()
                .map(ResumenMedioPago::getImporteTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        model.addAttribute("resumen", resumen);
        model.addAttribute("pagos", pagos);
        model.addAttribute("totalAbonos", total);

        return "reporte-medios-pago";
    }
}
