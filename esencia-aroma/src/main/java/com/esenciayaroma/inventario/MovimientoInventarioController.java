
package com.esenciayaroma.inventario;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario/movimientos")
public class MovimientoInventarioController {

    private final MovimientoInventarioService movimientoService;
    private final InventarioService inventarioService;

    public MovimientoInventarioController(
            MovimientoInventarioService movimientoService,
            InventarioService inventarioService) {

        this.movimientoService = movimientoService;
        this.inventarioService = inventarioService;
    }

    // MOSTRAR HISTORIAL
    @GetMapping
    public String mostrarHistorial(Model model) {

        model.addAttribute(
                "movimientos",
                movimientoService.listarMovimientos()
        );

        return "movimientos";
    }

    // MOSTRAR FORMULARIO DE MOVIMIENTO
    @GetMapping("/nuevo")
    public String mostrarFormulario(
            @RequestParam(required = false) Long productoId,
            Model model) {

        MovimientoFormulario formulario =
                new MovimientoFormulario();

        formulario.setProductoId(productoId);

        model.addAttribute("formulario", formulario);
        cargarOpciones(model);

        return "movimiento-form";
    }

    // REGISTRAR MOVIMIENTO
    @PostMapping("/nuevo")
    public String registrarMovimiento(
            @Valid @ModelAttribute("formulario")
            MovimientoFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarOpciones(model);
            return "movimiento-form";
        }

        try {

            movimientoService.registrarMovimiento(formulario);

        } catch (IllegalArgumentException e) {

            resultado.reject(
                    "movimiento.invalido",
                    e.getMessage()
            );

            cargarOpciones(model);
            return "movimiento-form";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Movimiento registrado correctamente."
        );

        return "redirect:/inventario/movimientos";
    }

    private void cargarOpciones(Model model) {

        model.addAttribute(
                "productos",
                inventarioService.listarProductos()
        );

        model.addAttribute(
                "tipos",
                TipoMovimiento.values()
        );
    }
}
