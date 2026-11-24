package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.ProductoRepository;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/abastecimiento/pedidos")
public class PedidoAbastecimientoController {

    private final PedidoAbastecimientoService pedidoService;
    private final ProveedorService proveedorService;
    private final CampaniaService campaniaService;
    private final ProductoRepository productoRepository;

    public PedidoAbastecimientoController(
            PedidoAbastecimientoService pedidoService,
            ProveedorService proveedorService,
            CampaniaService campaniaService,
            ProductoRepository productoRepository) {

        this.pedidoService = pedidoService;
        this.proveedorService = proveedorService;
        this.campaniaService = campaniaService;
        this.productoRepository = productoRepository;
    }

    // Mostrar pedidos
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "pedidos",
                pedidoService.listarPedidos()
        );

        return "pedidos-abastecimiento";
    }

    // Mostrar formulario
    @GetMapping("/nuevo")
    public String nuevo(Model model) {

        model.addAttribute(
                "formulario",
                new PedidoAbastecimientoFormulario()
        );

        cargarDatos(model);

        return "pedido-abastecimiento-form";
    }

    // Registrar pedido
    @PostMapping("/nuevo")
    public String registrar(
            @Valid @ModelAttribute("formulario")
            PedidoAbastecimientoFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarDatos(model);
            return "pedido-abastecimiento-form";
        }

        try {
            pedidoService.registrarPedido(formulario);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Pedido registrado correctamente."
            );

            return "redirect:/abastecimiento/pedidos";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());
            cargarDatos(model);

            return "pedido-abastecimiento-form";
        }
    }

    // Cargar información para los formularios
    private void cargarDatos(Model model) {

        model.addAttribute(
                "proveedores",
                proveedorService.listarProveedores()
        );

        model.addAttribute(
                "campanias",
                campaniaService.listarCampanias()
        );

        model.addAttribute(
                "productos",
                productoRepository.findAll()
        );

        model.addAttribute(
                "prioridades",
                PrioridadPedido.values()
        );
    }
}
