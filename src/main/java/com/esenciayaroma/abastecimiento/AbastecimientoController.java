
package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.InventarioService;
import com.esenciayaroma.inventario.Marca;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/abastecimiento")
public class AbastecimientoController {

    private final RecepcionService recepcionService;
    private final InventarioService inventarioService;

    public AbastecimientoController(
            RecepcionService recepcionService,
            InventarioService inventarioService) {

        this.recepcionService = recepcionService;
        this.inventarioService = inventarioService;
    }

    // PAGINA PRINCIPAL DEL MODULO
    @GetMapping
    public String mostrarAbastecimiento(Model model) {

        model.addAttribute(
                "recepciones",
                recepcionService.listarRecepciones()
        );

        return "abastecimiento";
    }

    // FORMULARIO PARA NUEVA RECEPCION
    @GetMapping("/nueva")
    public String nuevaRecepcion(Model model) {

        model.addAttribute(
                "formulario",
                new RecepcionFormulario()
        );

        cargarOpciones(model);

        return "recepcion-form";
    }

    // REGISTRAR RECEPCION
    @PostMapping("/nueva")
    public String registrarRecepcion(
            @Valid @ModelAttribute("formulario")
            RecepcionFormulario formulario,

            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarOpciones(model);
            return "recepcion-form";
        }

        try {

            recepcionService.registrarRecepcion(formulario);

        } catch (IllegalArgumentException e) {

            resultado.reject(
                    "recepcion.invalida",
                    e.getMessage()
            );

            cargarOpciones(model);
            return "recepcion-form";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Recepción registrada correctamente. " +
                        "El inventario fue actualizado."
        );

        return "redirect:/abastecimiento";
    }

    // DATOS PARA LOS SELECTORES
    private void cargarOpciones(Model model) {

        model.addAttribute(
                "marcas",
                Marca.values()
        );

        model.addAttribute(
                "productos",
                inventarioService.listarProductos()
        );
    }
}

