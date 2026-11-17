
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
@RequestMapping("/cobranzas")
public class CobranzasReportesController {

    private final CobranzaService cobranzaService;

    public CobranzasReportesController(
            CobranzaService cobranzaService) {

        this.cobranzaService = cobranzaService;
    }

    // PAGINA PRINCIPAL DE COBRANZAS
    @GetMapping
    public String mostrarCobranzas(Model model) {

        List<Venta> creditos = cobranzaService.listarCreditos();

        model.addAttribute("creditos", creditos);

        model.addAttribute(
                "totalPendiente",
                creditos.stream()
                        .map(Venta::getSaldo)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return "cobranzas";
    }

    // MOSTRAR FORMULARIO DE ABONO
    @GetMapping("/abonar/{id}")
    public String formularioAbono(
            @PathVariable Long id,
            Model model,
            RedirectAttributes atributos) {

        Venta venta = cobranzaService.obtenerVenta(id);

        if (venta.getSaldo().compareTo(BigDecimal.ZERO) == 0) {

            atributos.addFlashAttribute(
                    "mensaje",
                    "La venta ya está pagada."
            );

            return "redirect:/cobranzas";
        }

        model.addAttribute("formulario", new AbonoFormulario());
        cargarDetalle(id, model);

        return "abono-form";
    }

    // GUARDAR ABONO
    @PostMapping("/abonar/{id}")
    public String registrarAbono(
            @PathVariable Long id,
            @Valid @ModelAttribute("formulario")
            AbonoFormulario formulario,
            BindingResult resultado,
            Model model,
            RedirectAttributes atributos) {

        if (resultado.hasErrors()) {
            cargarDetalle(id, model);
            return "abono-form";
        }

        try {
            cobranzaService.registrarAbono(id, formulario);

        } catch (IllegalArgumentException e) {

            resultado.reject(
                    "abono.invalido",
                    e.getMessage()
            );

            cargarDetalle(id, model);
            return "abono-form";
        }

        atributos.addFlashAttribute(
                "mensaje",
                "Abono registrado correctamente."
        );

        return "redirect:/cobranzas";
    }

    // REPORTES BASICOS
    @GetMapping("/reportes")
    public String mostrarReportes(Model model) {

        List<Venta> ventas =
                cobranzaService.listarTodasVentas();

        BigDecimal vendido = ventas.stream()
                .map(Venta::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal cobrado = ventas.stream()
                .map(Venta::getMontoPagado)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal pendiente = ventas.stream()
                .map(Venta::getSaldo)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        long ventasPendientes = ventas.stream()
                .filter(v -> v.getSaldo()
                        .compareTo(BigDecimal.ZERO) > 0)
                .count();

        model.addAttribute("ventas", ventas);
        model.addAttribute("totalVendido", vendido);
        model.addAttribute("totalCobrado", cobrado);
        model.addAttribute("totalPendiente", pendiente);
        model.addAttribute("ventasPendientes", ventasPendientes);

        return "reportes";
    }

    private void cargarDetalle(Long id, Model model) {

        model.addAttribute(
                "venta",
                cobranzaService.obtenerVenta(id)
        );

        model.addAttribute(
                "abonos",
                cobranzaService.listarAbonos(id)
        );
    }
}
