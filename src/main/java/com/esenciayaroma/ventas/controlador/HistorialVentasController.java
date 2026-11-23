package com.esenciayaroma.ventas.controlador;

import com.esenciayaroma.ventas.ClientesVentasService;
import com.esenciayaroma.ventas.soporte.RutasVentas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(RutasVentas.BASE + RutasVentas.HISTORIAL)
public class HistorialVentasController {

    private final ClientesVentasService service;

    public HistorialVentasController(ClientesVentasService service) {
        this.service = service;
    }

    @GetMapping
    public String historial(Model model) {

        model.addAttribute("ventas", service.listarVentas());

        return RutasVentas.VISTA_HISTORIAL;
    }
}
