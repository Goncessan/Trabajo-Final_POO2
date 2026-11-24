
package com.esenciayaroma.inventario;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario/categorias")
public class CategoriaInventarioController {

    private final CategoriaInventarioService categoriaService;

    public CategoriaInventarioController(
            CategoriaInventarioService categoriaService) {

        this.categoriaService = categoriaService;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("categoria", new CategoriaInventario());

        return "categorias-inventario";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute("categorias", categoriaService.listar());
        model.addAttribute("categoria", categoriaService.obtener(id));

        return "categorias-inventario";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("categoria")
            CategoriaInventario categoria,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {

            model.addAttribute(
                    "categorias",
                    categoriaService.listar()
            );

            return "categorias-inventario";
        }

        try {

            categoriaService.guardar(categoria);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Categoría guardada correctamente."
            );

            return "redirect:/inventario/categorias";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());

            model.addAttribute(
                    "categorias",
                    categoriaService.listar()
            );

            return "categorias-inventario";
        }
    }

    @PostMapping("/eliminar/{id}")
    public String eliminar(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        try {

            categoriaService.eliminar(id);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Categoría eliminada correctamente."
            );

        } catch (IllegalArgumentException e) {

            atributos.addFlashAttribute(
                    "error",
                    e.getMessage()
            );
        }

        return "redirect:/inventario/categorias";
    }
}
