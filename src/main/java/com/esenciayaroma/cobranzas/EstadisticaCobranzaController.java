
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cobranzas/estadisticas")
public class EstadisticaCobranzaController {

    private final EstadisticaCobranzaService estadisticaService;

    public EstadisticaCobranzaController(
            EstadisticaCobranzaService estadisticaService) {

        this.estadisticaService = estadisticaService;
    }

    @GetMapping
    public String mostrarEstadisticas(
            @RequestParam(
                    name = "periodo",
                    defaultValue = "mensual"
            )
            String periodo,
            Model model) {

        EstadisticaCobranza estadistica =
                estadisticaService.generar(periodo);

        model.addAttribute("estadistica", estadistica);
        model.addAttribute("periodo", estadistica.getPeriodo());

        return "estadisticas-cobranza";
    }
}
