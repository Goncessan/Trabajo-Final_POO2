package com.esenciayaroma.ventas.controlador;

import com.esenciayaroma.ventas.Cliente;
import com.esenciayaroma.ventas.ClientesVentasService;
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
@RequestMapping(RutasVentas.BASE + RutasVentas.CLIENTES)
public class ClienteController {

    private final ClientesVentasService service;

    public ClienteController(ClientesVentasService service) {
        this.service = service;
    }

    @GetMapping
    public String clientes(Model model) {

        model.addAttribute("cliente", new Cliente());
        cargarClientes(model);

        return RutasVentas.VISTA_CLIENTES;
    }

    @PostMapping
    public String registrarCliente(
            @Valid @ModelAttribute("cliente") Cliente cliente,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarClientes(model);
            return RutasVentas.VISTA_CLIENTES;
        }

        service.registrarCliente(cliente);

        atributos.addFlashAttribute(
                "mensaje",
                MensajesVenta.CLIENTE_REGISTRADO
        );

        return RutasVentas.REDIRIGIR_CLIENTES;
    }

    private void cargarClientes(Model model) {
        model.addAttribute("clientes", service.listarClientes());
    }
}
