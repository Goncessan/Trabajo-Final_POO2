
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/cobranzas/comprobantes")
public class ComprobanteAbonoController {

    private final ComprobanteAbonoService comprobanteService;

    public ComprobanteAbonoController(
            ComprobanteAbonoService comprobanteService) {

        this.comprobanteService = comprobanteService;
    }

    @GetMapping("/{id}")
    public String mostrarComprobante(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "comprobante",
                comprobanteService.generar(id)
        );

        return "comprobante-abono";
    }
}
