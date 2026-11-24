
package com.esenciayaroma.inventario;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Set;
import java.util.TreeSet;

@Controller
@RequestMapping("/inventario")
public class InventarioController {

    private final InventarioFacade inventarioFacade;
    private final InventarioService inventarioService;
    private final CategoriaInventarioService categoriaService;

    public InventarioController(
            InventarioFacade inventarioFacade,
            InventarioService inventarioService,
            CategoriaInventarioService categoriaService) {

        this.inventarioFacade = inventarioFacade;
        this.inventarioService = inventarioService;
        this.categoriaService = categoriaService;
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

        ResumenInventario resumen =
                inventarioFacade.obtenerResumen(
                        busqueda,
                        marca,
                        categoria,
                        soloBajo
                );

        model.addAttribute(
                "productos",
                resumen.getProductos()
        );

        model.addAttribute(
                "totalProductos",
                resumen.getTotalProductos()
        );

        model.addAttribute("marcas", Marca.values());

        model.addAttribute(
                "categorias",
                resumen.getCategorias()
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

        cargarOpcionesFormulario(model);

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
                && producto.getCodigo() != null
                && inventarioFacade.existeCodigo(
                producto.getCodigo())) {

            resultado.rejectValue(
                    "codigo",
                    "codigo.duplicado",
                    "Ya existe un producto con ese código."
            );
        }

        if (resultado.hasErrors()) {

            cargarOpcionesFormulario(model);

            return "producto-form";
        }

        inventarioFacade.guardarProducto(producto);

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
                inventarioFacade.obtenerProducto(id);

        model.addAttribute("producto", producto);

        cargarOpcionesFormulario(model);

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
                && producto.getCodigo() != null
                && inventarioFacade
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

            cargarOpcionesFormulario(model);

            return "producto-form";
        }

        inventarioFacade.actualizarProducto(id, producto);

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

        boolean eliminado =
                inventarioFacade.eliminarProducto(id);

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

    // CARGAR MARCAS Y CATEGORÍAS PARA EL FORMULARIO

    private void cargarOpcionesFormulario(Model model) {

        model.addAttribute("marcas", Marca.values());

        Set<String> categoriasDisponibles =
                new TreeSet<>(String.CASE_INSENSITIVE_ORDER);

        // Categorías administradas desde la web

        categoriaService.listar()
                .stream()
                .map(CategoriaInventario::getNombre)
                .filter(nombre -> nombre != null
                        && !nombre.isBlank())
                .forEach(categoriasDisponibles::add);

        // Mantener categorías de productos existentes

        inventarioService.listarCategorias()
                .stream()
                .filter(nombre -> nombre != null
                        && !nombre.isBlank())
                .forEach(categoriasDisponibles::add);

        model.addAttribute(
                "categoriasDisponibles",
                categoriasDisponibles
        );
    }
}
