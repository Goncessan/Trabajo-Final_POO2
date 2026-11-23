package com.esenciayaroma.ventas.controlador;

import com.esenciayaroma.inventario.Marca;
import com.esenciayaroma.ventas.ClientesVentasService;
import com.esenciayaroma.ventas.PedidoClienteFormulario;
import com.esenciayaroma.ventas.soporte.MensajesVenta;
import com.esenciayaroma.ventas.soporte.RutasVentas;

import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(RutasVentas.BASE + RutasVentas.PEDIDOS)
public class PedidoClienteController {

    private final ClientesVentasService service;

    public PedidoClienteController(ClientesVentasService service) {
        this.service = service;
    }

    @GetMapping
    public String pedidos(Model model) {

        model.addAttribute(
                "formulario",
                new PedidoClienteFormulario()
        );

        cargarPedidos(model);

        return RutasVentas.VISTA_PEDIDOS;
    }

    @PostMapping
    public String registrarPedido(
            @Valid @ModelAttribute("formulario")
            PedidoClienteFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarPedidos(model);
            return RutasVentas.VISTA_PEDIDOS;
        }

        try {
            service.registrarPedido(formulario);

        } catch (IllegalArgumentException e) {
            resultado.reject(
                    MensajesVenta.CODIGO_PEDIDO_INVALIDO,
                    e.getMessage()
            );
            cargarPedidos(model);
            return RutasVentas.VISTA_PEDIDOS;
        }

        atributos.addFlashAttribute(
                "mensaje",
                MensajesVenta.ENCARGO_REGISTRADO
        );

        return RutasVentas.REDIRIGIR_PEDIDOS;
    }

    private void cargarPedidos(Model model) {

        model.addAttribute("clientes", service.listarClientes());
        model.addAttribute("pedidos", service.listarPedidos());
        model.addAttribute("marcas", Marca.values());
    }
}
