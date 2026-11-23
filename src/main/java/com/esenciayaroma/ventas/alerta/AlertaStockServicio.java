package com.esenciayaroma.ventas.alerta;

import com.esenciayaroma.ventas.observador.EventoVenta;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AlertaStockServicio {

    private final AlertaStockRepository repositorio;
    private final GeneradorMensajeAlerta generador;

    public AlertaStockServicio(
            AlertaStockRepository repositorio,
            GeneradorMensajeAlerta generador) {

        this.repositorio = repositorio;
        this.generador = generador;
    }

    /**
     * Crea la alerta solo si no hay otra igual pendiente para el producto.
     * Se ejecuta dentro de la transacción de la venta.
     */
    @Transactional
    public void crearSiNoExiste(EventoVenta evento, TipoAlerta tipo) {

        if (repositorio.existsByProductoIdAndTipoAndAtendidaFalse(
                evento.productoId(), tipo)) {
            return;
        }

        AlertaStock alerta = new AlertaStock(
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
    public List<AlertaStock> listarPendientes() {
        return repositorio.findByAtendidaFalseOrderByFechaCreacionDesc();
    }

    @Transactional(readOnly = true)
    public long contarPendientes() {
        return repositorio.countByAtendidaFalse();
    }

    @Transactional
    public void atender(Long id) {

        AlertaStock alerta = repositorio.findById(id)
                .orElseThrow(AlertaNoEncontradaException::new);

        alerta.marcarAtendida();
        repositorio.save(alerta);
    }
}
