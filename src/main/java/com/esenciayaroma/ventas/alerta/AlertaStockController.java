package com.esenciayaroma.ventas.alerta;

import com.esenciayaroma.ventas.soporte.MensajesVenta;
import com.esenciayaroma.ventas.soporte.RutasVentas;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(RutasVentas.BASE + RutasVentas.ALERTAS)
public class AlertaStockController {

    private final AlertaStockServicio servicio;

    public AlertaStockController(AlertaStockServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute("alertas", servicio.listarPendientes());

        return RutasVentas.VISTA_ALERTAS;
    }

    @PostMapping(RutasVentas.ATENDER_ALERTA)
    public String atender(
            @PathVariable("id") Long id,
            RedirectAttributes atributos) {

        servicio.atender(id);

        atributos.addFlashAttribute(
                "mensaje",
                MensajesVenta.ALERTA_ATENDIDA
        );

        return RutasVentas.REDIRIGIR_ALERTAS;
    }
}
