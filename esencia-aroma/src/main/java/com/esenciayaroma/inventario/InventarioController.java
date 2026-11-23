
package com.esenciayaroma.inventario;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioService inventarioService;

    public InventarioController(
            InventarioService inventarioService) {

        this.inventarioService = inventarioService;
    }

    // MOSTRAR INVENTARIO CON FILTROS
    @GetMapping
    public String mostrarInventario(
            @RequestParam(name = "busqueda", defaultValue = "")
            String busqueda,

            @RequestParam(name = "marca", required = false)
            Marca marca,

            @RequestParam(name = "categoria", defaultValue = "")
            String categoria,

            @RequestParam(name = "soloBajo", defaultValue = "false")
            boolean soloBajo,

            Model model) {

        model.addAttribute(
                "productos",
                inventarioService.filtrarProductos(
                        busqueda,
                        marca,
                        categoria,
                        soloBajo
                )
        );

        model.addAttribute(
                "totalProductos",
                inventarioService.contarProductos()
        );

        model.addAttribute("marcas", Marca.values());

        model.addAttribute(
                "categorias",
                inventarioService.listarCategorias()
        );

        model.addAttribute("busqueda", busqueda);

        model.addAttribute(
                "marcaSeleccionada",
                marca == null ? "" : marca.name()
        );

        model.addAttribute(
                "categoriaSeleccionada",
                categoria
        );

        model.addAttribute("soloBajo", soloBajo);

        return "inventario";
    }

    // FORMULARIO NUEVO PRODUCTO
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {

        model.addAttribute("producto", new Producto());
        model.addAttribute("marcas", Marca.values());

        return "producto-form";
    }

    // REGISTRAR PRODUCTO
    @PostMapping("/nuevo")
    public String registrarProducto(
            @Valid @ModelAttribute("producto")
            Producto producto,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (!resultado.hasFieldErrors("codigo")
                && inventarioService.existeCodigo(
                producto.getCodigo())) {

            resultado.rejectValue(
                    "codigo",
                    "codigo.duplicado",
                    "Ya existe un producto con ese código."
            );
        }

        if (resultado.hasErrors()) {

            model.addAttribute("marcas", Marca.values());

            return "producto-form";
        }

        inventarioService.guardarProducto(producto);

        atributos.addFlashAttribute(
                "mensaje",
                "Producto registrado correctamente."
        );

        return "redirect:/inventario";
    }

    // FORMULARIO EDITAR PRODUCTO
    @GetMapping("/editar/{id}")
    public String mostrarEdicion(
            @PathVariable Long id,
            Model model) {

        Producto producto =
                inventarioService.obtenerPorId(id);

        model.addAttribute("producto", producto);
        model.addAttribute("marcas", Marca.values());

        return "producto-form";
    }

    // ACTUALIZAR PRODUCTO
    @PostMapping("/editar/{id}")
    public String actualizarProducto(
            @PathVariable Long id,
            @Valid @ModelAttribute("producto")
            Producto producto,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (!resultado.hasFieldErrors("codigo")
                && inventarioService
                .existeOtroProductoConCodigo(
                        producto.getCodigo(), id)) {

            resultado.rejectValue(
                    "codigo",
                    "codigo.duplicado",
                    "Ese código pertenece a otro producto."
            );
        }

        if (resultado.hasErrors()) {

            producto.setId(id);

            model.addAttribute("marcas", Marca.values());

            return "producto-form";
        }

        inventarioService.actualizarProducto(id, producto);

        atributos.addFlashAttribute(
                "mensaje",
                "Producto actualizado correctamente."
        );

        return "redirect:/inventario";
    }

    // ELIMINAR PRODUCTO

    @PostMapping("/eliminar/{id}")
    public String eliminarProducto(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        boolean eliminado = inventarioService.eliminarProducto(id);

        if (eliminado) {
            atributos.addFlashAttribute(
                    "mensaje",
                    "Producto eliminado correctamente."
            );
        } else {
            atributos.addFlashAttribute(
                    "error",
                    "No se puede eliminar el producto porque tiene movimientos o recepciones registrados."
            );
        }

        return "redirect:/inventario";
    }



}