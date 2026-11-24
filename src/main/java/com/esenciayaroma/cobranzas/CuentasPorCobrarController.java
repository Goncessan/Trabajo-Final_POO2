
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/cobranzas/cuentas-por-cobrar")
public class CuentasPorCobrarController {

    private final CuentasPorCobrarService cuentasService;

    public CuentasPorCobrarController(
            CuentasPorCobrarService cuentasService) {

        this.cuentasService = cuentasService;
    }

    @GetMapping
    public String mostrarCuentas(
            @RequestParam(
                    name = "busqueda",
                    defaultValue = ""
            )
            String busqueda,
            Model model) {

        model.addAttribute(
                "cuentas",
                cuentasService.listarPendientes(busqueda)
        );

        model.addAttribute(
                "resumen",
                cuentasService.generarResumen()
        );

        model.addAttribute("busqueda", busqueda);

        return "cuentas-por-cobrar";
    }
}
