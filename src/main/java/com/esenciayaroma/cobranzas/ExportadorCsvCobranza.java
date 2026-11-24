
package com.esenciayaroma.cobranzas;

import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.format.DateTimeFormatter;

@Component
public class ExportadorCsvCobranza
        implements ExportadorCobranza {

    @Override
    public byte[] exportar(ReporteCobranza reporte) {

        StringBuilder csv = new StringBuilder();

        // BOM para facilitar la lectura de Excel
        csv.append('\uFEFF');

        csv.append(
                "Fecha;Venta;Medio de pago;Referencia;Importe;Observacion\n"
        );

        DateTimeFormatter formato =
                DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        for (Abono abono : reporte.getAbonos()) {

            String fecha = abono.getFechaRegistro()
                    .format(formato);

            String venta = String.valueOf(
                    abono.getVenta().getId()
            );

            String medio = abono.getMedioPago() == null
                    ? "No especificado"
                    : abono.getMedioPago().getNombre();

            String referencia =
                    abono.getReferenciaOperacion() == null
                            ? ""
                            : abono.getReferenciaOperacion();

            String observacion =
                    abono.getObservacion() == null
                            ? ""
                            : abono.getObservacion();

            csv.append(celda(fecha)).append(';')
                    .append(celda(venta)).append(';')
                    .append(celda(medio)).append(';')
                    .append(celda(referencia)).append(';')
                    .append(abono.getMonto()).append(';')
                    .append(celda(observacion))
                    .append('\n');
        }

        csv.append("\nTotal de operaciones;")
                .append(reporte.getCantidad())
                .append('\n');

        csv.append("Importe total;")
                .append(reporte.getTotal())
                .append('\n');

        return csv.toString().getBytes(
                StandardCharsets.UTF_8
        );
    }

    private String celda(String valor) {

        if (valor == null) {
            return "\"\"";
        }

        return "\"" +
                valor.replace("\"", "\"\"") +
                "\"";
    }
}
