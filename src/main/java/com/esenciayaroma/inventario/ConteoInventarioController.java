
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario/conteos")
public class ConteoInventarioController {

    private final ConteoInventarioService conteoService;

    public ConteoInventarioController(
            ConteoInventarioService conteoService) {

        this.conteoService = conteoService;
    }

    // MOSTRAR HISTORIAL DE CONTEOS

    @GetMapping
    public String listarConteos(Model model) {

        model.addAttribute(
                "conteos",
                conteoService.listarConteos()
        );

        return "conteos-inventario";
    }

    // MOSTRAR FORMULARIO

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute(
                "formulario",
                conteoService.prepararFormulario()
        );

        return "conteo-form";
    }

    // REGISTRAR CONTEO

    @PostMapping("/nuevo")
    public String registrarConteo(
            @ModelAttribute("formulario")
            ConteoInventarioFormulario formulario,
            RedirectAttributes atributos) {

        try {

            Long id = conteoService.registrarConteo(formulario);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Conteo físico registrado correctamente."
            );

            return "redirect:/inventario/conteos/" + id;

        } catch (IllegalArgumentException e) {

            atributos.addFlashAttribute(
                    "error",
                    e.getMessage()
            );

            return "redirect:/inventario/conteos/nuevo";
        }
    }

    // MOSTRAR RESULTADO DEL CONTEO

    @GetMapping("/{id}")
    public String mostrarConteo(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "conteo",
                conteoService.obtenerConteo(id)
        );

        model.addAttribute(
                "detalles",
                conteoService.listarDetalles(id)
        );

        return "detalle-conteo";
    }

    // CONFIRMAR AJUSTES

    @PostMapping("/{id}/confirmar")
    public String confirmarConteo(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        try {

            conteoService.confirmarConteo(id);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Conteo confirmado y existencias actualizadas correctamente."
            );

        } catch (IllegalArgumentException |
                 IllegalStateException e) {

            atributos.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/inventario/conteos/" + id;
    }
}
