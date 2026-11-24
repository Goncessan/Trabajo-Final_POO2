package com.esenciayaroma.ventas.alerta;

import com.esenciayaroma.ventas.observador.EventoVenta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertaStockVentaServicio {

    private final AlertaStockVentaRepository repositorio;
    private final GeneradorMensajeAlerta generador;

    public AlertaStockVentaServicio(
            AlertaStockVentaRepository repositorio,
            GeneradorMensajeAlerta generador) {

        this.repositorio = repositorio;
        this.generador = generador;
    }

    /**
     * Crea la alerta solo si no hay otra igual pendiente para el producto.
     * Se ejecuta dentro de la transacción de la venta.
     */
    @Transactional
    public void crearSiNoExiste(EventoVenta evento, TipoAlertaStock tipo) {

        if (repositorio.existsByProductoIdAndTipoAndAtendidaFalse(
                evento.productoId(), tipo)) {
            return;
        }

        AlertaStockVenta alerta = new AlertaStockVenta(
                evento.productoId(),
                evento.productoNombre(),
                tipo,
                evento.stockActual(),
                evento.stockMinimo(),
                generador.crear(evento, tipo)
        );

        repositorio.save(alerta);
    }

    @Transactional(readOnly = true)
    public List<AlertaStockVenta> listarPendientes() {
        return repositorio.findByAtendidaFalseOrderByFechaCreacionDesc();
    }

    @Transactional(readOnly = true)
    public long contarPendientes() {
        return repositorio.countByAtendidaFalse();
    }

    @Transactional
    public void atender(Long id) {

        AlertaStockVenta alerta = repositorio.findById(id)
                .orElseThrow(AlertaNoEncontradaException::new);

        alerta.marcarAtendida();
        repositorio.save(alerta);
    }
}
