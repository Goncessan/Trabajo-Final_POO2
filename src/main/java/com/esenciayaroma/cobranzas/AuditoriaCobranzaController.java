package com.esenciayaroma.cobranzas;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/cobranzas/auditoria")
public class AuditoriaCobranzaController {

    private final AuditoriaCobranzaService auditoriaService;
    private final ResumenAuditoriaCobranzaService resumenService;

    public AuditoriaCobranzaController(
            AuditoriaCobranzaService auditoriaService,
            ResumenAuditoriaCobranzaService resumenService) {

        this.auditoriaService = auditoriaService;
        this.resumenService = resumenService;
    }

    @GetMapping
    public String mostrarAuditoria(

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate desde,

            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate hasta,

            @RequestParam(required = false)
            String usuario,

            @RequestParam(required = false)
            String medioPago,

            @RequestParam(required = false)
            Long ventaId,

            Model model) {

        model.addAttribute(
                "registros",
                auditoriaService.filtrar(
                        desde,
                        hasta,
                        usuario,
                        medioPago,
                        ventaId
                )
        );

        model.addAttribute(
                "resumen",
                resumenService.generarResumen()
        );

        model.addAttribute("desde", desde);
        model.addAttribute("hasta", hasta);
        model.addAttribute("usuario", usuario);
        model.addAttribute("medioPago", medioPago);
        model.addAttribute("ventaId", ventaId);

        return "auditoria-cobranzas";
    }
}