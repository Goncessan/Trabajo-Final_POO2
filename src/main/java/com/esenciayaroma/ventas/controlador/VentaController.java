package com.esenciayaroma.ventas.controlador;

import com.esenciayaroma.ventas.ClientesVentasService;
import com.esenciayaroma.ventas.FormaPago;
import com.esenciayaroma.ventas.VentaFormulario;
import com.esenciayaroma.ventas.concurrencia.VentaFachada;
import com.esenciayaroma.ventas.puerto.PuertoInventario;
import com.esenciayaroma.ventas.regla.FiltroPedidosPendientes;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(RutasVentas.BASE + RutasVentas.REGISTRAR)
public class VentaController {

    private final ClientesVentasService service;
    private final VentaFachada fachada;
    private final PuertoInventario inventario;
    private final FiltroPedidosPendientes filtroPendientes;

    public VentaController(
            ClientesVentasService service,
            VentaFachada fachada,
            PuertoInventario inventario,
            FiltroPedidosPendientes filtroPendientes) {

        this.service = service;
        this.fachada = fachada;
        this.inventario = inventario;
        this.filtroPendientes = filtroPendientes;
    }

    @GetMapping
    public String formularioVenta(
            @RequestParam(name = "pedidoId", required = false) Long pedidoId,
            Model model) {

        model.addAttribute(
                "formulario",
                service.prepararFormularioVenta(pedidoId)
        );

        cargarOpcionesVenta(model);

        return RutasVentas.VISTA_VENTA;
    }

    @PostMapping
    public String registrarVenta(
            @Valid @ModelAttribute("formulario")
            VentaFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarOpcionesVenta(model);
            return RutasVentas.VISTA_VENTA;
        }

        try {
            fachada.registrarVenta(formulario);

        } catch (IllegalArgumentException e) {

            resultado.reject(
                    MensajesVenta.CODIGO_VENTA_INVALIDA,
                    e.getMessage()
            );

            cargarOpcionesVenta(model);
            return RutasVentas.VISTA_VENTA;
        }

        atributos.addFlashAttribute(
                "mensaje",
                MensajesVenta.VENTA_REGISTRADA
        );

        return RutasVentas.REDIRIGIR_HISTORIAL;
    }

    private void cargarOpcionesVenta(Model model) {

        model.addAttribute("clientes", service.listarClientes());

        model.addAttribute("productos", inventario.listarProductos());

        model.addAttribute(
                "pedidosPendientes",
                filtroPendientes.filtrar(service.listarPedidos())
        );

        model.addAttribute("formasPago", FormaPago.values());
    }
}
