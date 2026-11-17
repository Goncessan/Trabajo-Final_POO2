
package com.esenciayaroma.ventas;

import com.esenciayaroma.inventario.InventarioService;
import com.esenciayaroma.inventario.Marca;
import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ventas")
public class ClientesVentasController {

    private final ClientesVentasService service;
    private final InventarioService inventarioService;

    public ClientesVentasController(
            ClientesVentasService service,
            InventarioService inventarioService) {

        this.service = service;
        this.inventarioService = inventarioService;
    }

    @GetMapping
    public String inicio(Model model) {

        model.addAttribute("clientes", service.listarClientes());
        model.addAttribute("pedidos", service.listarPedidos());
        model.addAttribute("ventas", service.listarVentas());

        return "ventas";
    }

    // CLIENTES

    @GetMapping("/clientes")
    public String clientes(Model model) {

        model.addAttribute("cliente", new Cliente());
        cargarClientes(model);

        return "clientes";
    }

    @PostMapping("/clientes")
    public String registrarCliente(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarClientes(model);
            return "clientes";
        }

        service.registrarCliente(cliente);

        atributos.addFlashAttribute(
                "mensaje",
                "Cliente registrado correctamente."
        );

        return "redirect:/ventas/clientes";
    }

    // PEDIDOS DE CLIENTES

    @GetMapping("/pedidos")
    public String pedidos(Model model) {

        model.addAttribute(
                "formulario",
                new PedidoClienteFormulario()
        );

        cargarPedidos(model);

        return "pedidos-clientes";
    }

    @PostMapping("/pedidos")
    public String registrarPedido(
            @Valid @ModelAttribute("formulario")
            PedidoClienteFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarPedidos(model);
            return "pedidos-clientes";
        }

        try {
            service.registrarPedido(formulario);

        } catch (IllegalArgumentException e) {
            resultado.reject("pedido.invalido", e.getMessage());
            cargarPedidos(model);
            return "pedidos-clientes";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Encargo registrado correctamente."
        );

        return "redirect:/ventas/pedidos";
    }

    // FORMULARIO DE VENTAS

    @GetMapping("/registrar")
    public String formularioVenta(
            @RequestParam(required = false) Long pedidoId,
            Model model) {

        model.addAttribute(
                "formulario",
                service.prepararFormularioVenta(pedidoId)
        );

        cargarOpcionesVenta(model);

        return "venta-form";
    }

    @PostMapping("/registrar")
    public String registrarVenta(
            @Valid @ModelAttribute("formulario")
            VentaFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarOpcionesVenta(model);
            return "venta-form";
        }

        try {
            service.registrarVenta(formulario);

        } catch (IllegalArgumentException e) {

            resultado.reject("venta.invalida", e.getMessage());

            cargarOpcionesVenta(model);
            return "venta-form";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Venta registrada correctamente."
        );

        return "redirect:/ventas/historial";
    }

    // HISTORIAL DE VENTAS

    @GetMapping("/historial")
    public String historial(Model model) {

        model.addAttribute("ventas", service.listarVentas());

        return "ventas-historial";
    }

    private void cargarClientes(Model model) {
        model.addAttribute("clientes", service.listarClientes());
    }

    private void cargarPedidos(Model model) {

        model.addAttribute("clientes", service.listarClientes());
        model.addAttribute("pedidos", service.listarPedidos());
        model.addAttribute("marcas", Marca.values());
    }

    private void cargarOpcionesVenta(Model model) {

        model.addAttribute("clientes", service.listarClientes());

        model.addAttribute(
                "productos",
                inventarioService.listarProductos()
        );

        model.addAttribute(
                "pedidosPendientes",
                service.listarPedidos()
                        .stream()
                        .filter(p -> p.getCantidadPendiente() > 0)
                        .toList()
        );

        model.addAttribute("formasPago", FormaPago.values());
    }
}
