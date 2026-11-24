
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
    private final ProveedorService proveedorService;

    public AbastecimientoController(
            RecepcionService recepcionService,
            InventarioService inventarioService,
            ProveedorService proveedorService) {

        this.recepcionService = recepcionService;
        this.inventarioService = inventarioService;
        this.proveedorService = proveedorService;
    }

    // PAGINA PRINCIPAL
    @GetMapping
    public String mostrarAbastecimiento(Model model) {

        model.addAttribute(
                "recepciones",
                recepcionService.listarRecepciones()
        );

        return "abastecimiento";
    }

    // FORMULARIO DE RECEPCION
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

    // LISTAR PROVEEDORES
    @GetMapping("/proveedores")
    public String listarProveedores(Model model) {

        model.addAttribute(
                "proveedores",
                proveedorService.listarProveedores()
        );

        return "proveedores";
    }

    // FORMULARIO DE NUEVO PROVEEDOR
    @GetMapping("/proveedores/nuevo")
    public String nuevoProveedor(Model model) {

        model.addAttribute(
                "formulario",
                new ProveedorFormulario()
        );

        model.addAttribute(
                "tiposProveedor",
                TipoProveedor.values()
        );

        return "proveedor-form";
    }

    // REGISTRAR PROVEEDOR
    @PostMapping("/proveedores/nuevo")
    public String registrarProveedor(
            @Valid @ModelAttribute("formulario")
            ProveedorFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {

            model.addAttribute(
                    "tiposProveedor",
                    TipoProveedor.values()
            );

            return "proveedor-form";
        }

        try {

            proveedorService.registrarProveedor(formulario);

        } catch (IllegalArgumentException e) {

            resultado.reject(
                    "proveedor.invalido",
                    e.getMessage()
            );

            model.addAttribute(
                    "tiposProveedor",
                    TipoProveedor.values()
            );

            return "proveedor-form";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Proveedor registrado correctamente."
        );

        return "redirect:/abastecimiento/proveedores";
    }

    // DESACTIVAR PROVEEDOR
    @PostMapping("/proveedores/desactivar/{id}")
    public String desactivarProveedor(
            @PathVariable Long id,
            RedirectAttributes atributos) {

        proveedorService.desactivarProveedor(id);

        atributos.addFlashAttribute(
                "mensaje",
                "Proveedor desactivado correctamente."
        );

        return "redirect:/abastecimiento/proveedores";
    }

    // OPCIONES PARA RECEPCIONES
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
