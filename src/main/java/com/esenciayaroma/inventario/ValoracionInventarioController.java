
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ValoracionInventarioController {

    private final ValoracionInventarioService valoracionService;

    public ValoracionInventarioController(
            ValoracionInventarioService valoracionService) {

        this.valoracionService = valoracionService;
    }

    @GetMapping("/inventario/valoracion")
    public String mostrarValoracion(
            @RequestParam(defaultValue = "costo") String tipo,
            Model model) {

        String tipoSeleccionado =
                "venta".equalsIgnoreCase(tipo)
                        ? "venta"
                        : "costo";

        ResumenValoracion resumen =
                valoracionService.calcularValoracion(
                        tipoSeleccionado
                );

        model.addAttribute("resumen", resumen);
        model.addAttribute("tipoSeleccionado", tipoSeleccionado);

        return "valoracion-inventario";
    }
}
