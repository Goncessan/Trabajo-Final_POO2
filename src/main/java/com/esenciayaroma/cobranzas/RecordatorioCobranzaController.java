
package com.esenciayaroma.cobranzas;

import com.esenciayaroma.ventas.Venta;

import jakarta.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cobranzas/recordatorios")
public class RecordatorioCobranzaController {

    private final RecordatorioCobranzaService recordatorioService;
    private final CobranzaService cobranzaService;

    public RecordatorioCobranzaController(
            RecordatorioCobranzaService recordatorioService,
            CobranzaService cobranzaService) {

        this.recordatorioService = recordatorioService;
        this.cobranzaService = cobranzaService;
    }

    @GetMapping
    public String mostrar(Model model) {

        cargarDatos(model);

        model.addAttribute(
                "formulario",
                new VencimientoFormulario()
        );

        return "recordatorios-cobranza";
    }

    @PostMapping("/guardar")
    public String guardar(
            @Valid @ModelAttribute("formulario")
            VencimientoFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarDatos(model);
            return "recordatorios-cobranza";
        }

        try {
            recordatorioService.registrar(formulario);

            atributos.addFlashAttribute(
                    "mensaje",
                    "Fecha de vencimiento registrada correctamente."
            );

            return "redirect:/cobranzas/recordatorios";

        } catch (IllegalArgumentException e) {

            resultado.reject(
                    "vencimiento.invalido",
                    e.getMessage()
            );

            cargarDatos(model);
            return "recordatorios-cobranza";
        }
    }

    private void cargarDatos(Model model) {

        List<Venta> ventasPendientes =
                cobranzaService.listarCreditos()
                        .stream()
                        .filter(v ->
                                v.getSaldo() != null
                                        && v.getSaldo()
                                        .compareTo(BigDecimal.ZERO) > 0
                        )
                        .toList();

        model.addAttribute("ventasPendientes", ventasPendientes);

        model.addAttribute(
                "alertas",
                recordatorioService.listarAlertas()
        );

        model.addAttribute(
                "resumen",
                recordatorioService.generarResumen()
        );
    }
}
