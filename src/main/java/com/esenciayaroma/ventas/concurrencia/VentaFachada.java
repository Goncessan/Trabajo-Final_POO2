package com.esenciayaroma.ventas.concurrencia;

import com.esenciayaroma.ventas.ClientesVentasService;
import com.esenciayaroma.ventas.VentaFormulario;
import org.springframework.stereotype.Component;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Facade: punto único para registrar una venta.
 *
 * Toma el candado del producto ANTES de que empiece la transacción
 * y lo suelta DESPUÉS de que termina (commit). Por eso vive en una
 * clase distinta de ClientesVentasService: si el candado estuviera
 * dentro del método transaccional, se soltaría antes del commit.
 */
@Component
public class VentaFachada {

    private final BloqueoProducto bloqueo;
    private final ClientesVentasService servicio;
    private final ContadorVentasEnCurso contador;

    public VentaFachada(
            BloqueoProducto bloqueo,
            ClientesVentasService servicio,
            ContadorVentasEnCurso contador) {

        this.bloqueo = bloqueo;
        this.servicio = servicio;
        this.contador = contador;
    }

    public void registrarVenta(VentaFormulario formulario) {

        ReentrantLock candado = bloqueo.adquirir(formulario.getProductoId());
        contador.sumar();

        try {
            servicio.registrarVenta(formulario);
        } finally {
            contador.restar();
            bloqueo.liberar(candado);
        }
    }
}
