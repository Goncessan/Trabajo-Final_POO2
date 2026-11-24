
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inventario/auditoria")
public class AuditoriaInventarioController {

    private final AuditoriaInventarioService auditoriaService;

    public AuditoriaInventarioController(
            AuditoriaInventarioService auditoriaService) {

        this.auditoriaService = auditoriaService;
    }

    @GetMapping
    public String mostrarAuditoria(Model model) {

        model.addAttribute(
                "registros",
                auditoriaService.listarRegistros()
        );

        return "auditoria-inventario";
    }
}
