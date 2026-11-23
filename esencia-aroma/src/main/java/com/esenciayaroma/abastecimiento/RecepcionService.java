
package com.esenciayaroma.abastecimiento;

import com.esenciayaroma.inventario.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class RecepcionService {

    private final RecepcionRepository recepcionRepository;
    private final ProductoRepository productoRepository;
    private final MovimientoInventarioService movimientoService;

    public RecepcionService(
            RecepcionRepository recepcionRepository,
            ProductoRepository productoRepository,
            MovimientoInventarioService movimientoService) {

        this.recepcionRepository = recepcionRepository;
        this.productoRepository = productoRepository;
        this.movimientoService = movimientoService;
    }

    // MOSTRAR TODAS LAS RECEPCIONES
    @Transactional(readOnly = true)
    public List<RecepcionPedido> listarRecepciones() {

        return recepcionRepository
                .findAllByOrderByFechaRecepcionDescIdDesc();
    }

    // REGISTRAR RECEPCION DE MERCADERIA
    @Transactional
    public void registrarRecepcion(
            RecepcionFormulario formulario) {

        RecepcionPedido recepcion = new RecepcionPedido(
                formulario.getMarca(),
                formulario.getCampania().trim(),
                formulario.getFechaRecepcion()
        );

        Set<Long> productosRegistrados = new HashSet<>();

        // 1. VALIDAR LOS PRODUCTOS Y LAS CANTIDADES
        for (LineaRecepcionFormulario linea
                : formulario.getDetalles()) {

            Long productoId = linea.getProductoId();

            if (!productosRegistrados.add(productoId)) {
                throw new IllegalArgumentException(
                        "No puedes repetir el mismo producto " +
                                "en una recepción."
                );
            }

            Producto producto = productoRepository
                    .findById(productoId)
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Uno de los productos no existe."
                            )
                    );

            if (producto.getMarca() != formulario.getMarca()) {
                throw new IllegalArgumentException(
                        "El producto " + producto.getNombre()
                                + " no pertenece a la marca seleccionada."
                );
            }

            int solicitado = linea.getCantidadSolicitada();
            int recibido = linea.getCantidadRecibida();

            if (recibido > solicitado) {
                throw new IllegalArgumentException(
                        "La cantidad recibida no puede superar " +
                                "la solicitada para " + producto.getNombre()
                );
            }

            DetalleRecepcion detalle = new DetalleRecepcion(
                    producto,
                    linea.getCodigoCatalogo().trim(),
                    solicitado,
                    recibido
            );

            recepcion.agregarDetalle(detalle);
        }

        // 2. GUARDAR LA RECEPCION
        RecepcionPedido guardada =
                recepcionRepository.saveAndFlush(recepcion);

        // 3. SUMAR AL INVENTARIO SOLO LO RECIBIDO
        for (DetalleRecepcion detalle
                : guardada.getDetalles()) {

            if (detalle.getCantidadRecibida() > 0) {

                MovimientoFormulario movimiento =
                        new MovimientoFormulario();

                movimiento.setProductoId(
                        detalle.getProducto().getId()
                );

                movimiento.setTipo(TipoMovimiento.ENTRADA);

                movimiento.setCantidad(
                        detalle.getCantidadRecibida()
                );

                movimiento.setMotivo(
                        "Recepción " + guardada.getId()
                                + " - " + guardada.getMarca().getNombre()
                                + " - Campaña " + guardada.getCampania()
                );

                movimientoService.registrarMovimiento(movimiento);
            }
        }
    }
}
