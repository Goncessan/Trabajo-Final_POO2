package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.Marca;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/abastecimiento/campanias")
public class CampaniaController {

    private final CampaniaService campaniaService;

    public CampaniaController(CampaniaService campaniaService) {
        this.campaniaService = campaniaService;
    }

    // Mostrar campañas
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "campanias",
                campaniaService.listarCampanias()
        );

        return "campanias";
    }

    // Mostrar formulario
    @GetMapping("/nueva")
    public String nueva(Model model) {

        model.addAttribute(
                "formulario",
                new CampaniaFormulario()
        );

        model.addAttribute("marcas", Marca.values());

        return "campania-form";
    }

    // Registrar campaña
    @PostMapping("/nueva")
    public String registrar(
            @Valid @ModelAttribute("formulario")
            CampaniaFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            model.addAttribute("marcas", Marca.values());
            return "campania-form";
        }

        try {
            campaniaService.registrarCampania(formulario);

        } catch (IllegalArgumentException e) {
            resultado.reject("campania.invalida", e.getMessage());
            model.addAttribute("marcas", Marca.values());
            return "campania-form";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Campaña registrada correctamente."
        );

        return "redirect:/abastecimiento/campanias";
    }
}
