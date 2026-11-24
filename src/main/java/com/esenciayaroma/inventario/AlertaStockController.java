
package com.esenciayaroma.inventario;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;
import java.util.Optional;

@Controller
public class AlertaStockController {

    private final AlertaStockService alertaStockService;

    public AlertaStockController(
            AlertaStockService alertaStockService) {

        this.alertaStockService = alertaStockService;
    }

    // Página del historial de alertas
    @GetMapping("/inventario/alertas")
    public String mostrarAlertas(Model model) {

        model.addAttribute(
                "alertas",
                alertaStockService.listarAlertas()
        );

        return "alertas-stock";
    }

    // Consultar la última notificación
    @GetMapping("/inventario/alertas/ultima")
    public ResponseEntity<Map<String, Object>> obtenerUltimaAlerta() {

        Optional<AlertaStock> resultado =
                alertaStockService.obtenerUltimaAlertaReciente();

        if (resultado.isEmpty()) {

            return ResponseEntity.noContent().build();
        }

        AlertaStock alerta = resultado.get();

        return ResponseEntity.ok(
                Map.of(
                        "id", alerta.getId(),
                        "producto", alerta.getNombreProducto(),
                        "stock", alerta.getStockActual(),
                        "minimo", alerta.getStockMinimo()
                )
        );
    }
}
