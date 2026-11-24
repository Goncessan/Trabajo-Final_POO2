package com.esenciayaroma.ventas;

import com.esenciayaroma.ventas.alerta.AlertaStockVentaServicio;
import com.esenciayaroma.ventas.soporte.RutasVentas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Panel principal del módulo de ventas.
 * Clientes, encargos, ventas e historial tienen su propio controlador
 * en el paquete ventas.controlador.
 */
@Controller
@RequestMapping(RutasVentas.BASE)
public class ClientesVentasController {

    private final ClientesVentasService service;
    private final AlertaStockVentaServicio alertaServicio;

    public ClientesVentasController(
            ClientesVentasService service,
            AlertaStockVentaServicio alertaServicio) {

        this.service = service;
        this.alertaServicio = alertaServicio;
    }

    @GetMapping
    public String inicio(Model model) {

        model.addAttribute("clientes", service.listarClientes());
        model.addAttribute("pedidos", service.listarPedidos());
        model.addAttribute("ventas", service.listarVentas());
        model.addAttribute(
                "alertasPendientes",
                alertaServicio.contarPendientes()
        );

        return RutasVentas.VISTA_PANEL;
    }
}
