
package com.esenciayaroma.cobranzas;

import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.nio.charset.StandardCharsets;

@Controller
@RequestMapping("/cobranzas/exportar")
public class ExportacionCobranzaController {

    private final ExportacionCobranzaFacade exportacionFacade;

    public ExportacionCobranzaController(
            ExportacionCobranzaFacade exportacionFacade) {

        this.exportacionFacade = exportacionFacade;
    }

    @GetMapping
    public ResponseEntity<byte[]> exportar(
            @ModelAttribute FiltroReporteCobranza filtro) {

        try {

            byte[] archivo =
                    exportacionFacade.exportarCsv(filtro);

            HttpHeaders headers = new HttpHeaders();

            headers.setContentType(
                    new MediaType(
                            "text",
                            "csv",
                            StandardCharsets.UTF_8
                    )
            );

            headers.setContentDisposition(
                    ContentDisposition.attachment()
                            .filename("reporte-cobranzas.csv")
                            .build()
            );

            headers.setContentLength(archivo.length);

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(archivo);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest()
                    .contentType(MediaType.TEXT_PLAIN)
                    .body(e.getMessage().getBytes(
                            StandardCharsets.UTF_8
                    ));
        }
    }
}
