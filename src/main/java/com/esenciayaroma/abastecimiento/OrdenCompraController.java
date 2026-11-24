package com.esenciayaroma.abastecimiento;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/abastecimiento/ordenes")
public class OrdenCompraController {

    private final OrdenCompraService ordenService;
    private final PedidoAbastecimientoService pedidoService;

    public OrdenCompraController(
            OrdenCompraService ordenService,
            PedidoAbastecimientoService pedidoService) {

        this.ordenService = ordenService;
        this.pedidoService = pedidoService;
    }

    // Mostrar órdenes
    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "ordenes",
                ordenService.listarOrdenes()
        );

        return "ordenes-compra";
    }

    // Mostrar formulario
    @GetMapping("/nueva")
    public String nueva(Model model) {

        model.addAttribute(
                "formulario",
                new OrdenCompraFormulario()
        );

        model.addAttribute(
                "pedidos",
                pedidoService.listarPedidos()
        );

        return "orden-compra-form";
    }

    // Registrar orden
    @PostMapping("/nueva")
    public String registrar(
            @Valid @ModelAttribute("formulario")
            OrdenCompraFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            model.addAttribute(
                    "pedidos",
                    pedidoService.listarPedidos()
            );

            return "orden-compra-form";
        }

        try {
            ordenService.registrarOrden(formulario);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Orden de compra registrada correctamente."
            );

            return "redirect:/abastecimiento/ordenes";

        } catch (IllegalArgumentException e) {

            model.addAttribute("error", e.getMessage());

            model.addAttribute(
                    "pedidos",
                    pedidoService.listarPedidos()
            );

            return "orden-compra-form";
        }
    }
}
