
package com.esenciayaroma.inventario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ConteoInventarioService {

    private final ProductoRepository productoRepository;
    private final ConteoInventarioRepository conteoRepository;
    private final DetalleConteoInventarioRepository detalleRepository;
    private final MovimientoInventarioService movimientoService;

    public ConteoInventarioService(
            ProductoRepository productoRepository,
            ConteoInventarioRepository conteoRepository,
            DetalleConteoInventarioRepository detalleRepository,
            MovimientoInventarioService movimientoService) {

        this.productoRepository = productoRepository;
        this.conteoRepository = conteoRepository;
        this.detalleRepository = detalleRepository;
        this.movimientoService = movimientoService;
    }

    // PREPARAR FORMULARIO DE CONTEO

    @Transactional(readOnly = true)
    public ConteoInventarioFormulario prepararFormulario() {

        ConteoInventarioFormulario formulario =
                new ConteoInventarioFormulario();

        List<DetalleConteoFormulario> detalles =
                productoRepository.findAllByOrderByNombreAsc()
                        .stream()
                        .map(producto -> {

                            DetalleConteoFormulario detalle =
                                    new DetalleConteoFormulario();

                            detalle.setProductoId(producto.getId());
                            detalle.setCodigoProducto(producto.getCodigo());
                            detalle.setNombreProducto(producto.getNombre());
                            detalle.setStockSistema(producto.getStock());
                            detalle.setSeleccionado(true);

                            return detalle;
                        })
                        .toList();

        formulario.setTipoConteo("COMPLETO");
        formulario.setDetalles(new ArrayList<>(detalles));

        return formulario;
    }

    // REGISTRAR CONTEO FÍSICO

    @Transactional
    public Long registrarConteo(
            ConteoInventarioFormulario formulario) {

        if (formulario == null ||
                formulario.getDetalles() == null ||
                formulario.getDetalles().isEmpty()) {

            throw new IllegalArgumentException(
                    "No existen productos para realizar el conteo."
            );
        }

        String tipo = formulario.getTipoConteo();

        if (!"COMPLETO".equals(tipo) &&
                !"PARCIAL".equals(tipo)) {

            throw new IllegalArgumentException(
                    "Seleccione un tipo de conteo válido."
            );
        }

        List<DetalleConteoFormulario> seleccionados =
                formulario.getDetalles()
                        .stream()
                        .filter(DetalleConteoFormulario::isSeleccionado)
                        .toList();

        if (seleccionados.isEmpty()) {

            throw new IllegalArgumentException(
                    "Debe seleccionar al menos un producto."
            );
        }

        if ("COMPLETO".equals(tipo) &&
                seleccionados.size() != productoRepository.count()) {

            throw new IllegalArgumentException(
                    "En un conteo completo debe seleccionar todos los productos."
            );
        }

        // Evitar productos repetidos

        Set<Long> productosSeleccionados = new HashSet<>();

        for (DetalleConteoFormulario detalle : seleccionados) {

            if (detalle.getProductoId() == null ||
                    !productosSeleccionados.add(
                            detalle.getProductoId())) {

                throw new IllegalArgumentException(
                        "Hay productos inválidos o repetidos."
                );
            }

            if (detalle.getStockFisico() == null ||
                    detalle.getStockFisico() < 0) {

                throw new IllegalArgumentException(
                        "Ingrese una cantidad física válida para todos los productos seleccionados."
                );
            }
        }

        // Identificar al usuario

        Authentication autenticacion =
                SecurityContextHolder.getContext().getAuthentication();

        String usuario = "Sistema";

        if (autenticacion != null &&
                autenticacion.isAuthenticated() &&
                !"anonymousUser".equals(autenticacion.getName())) {

            usuario = autenticacion.getName();
        }

        // Crear la sesión de conteo

        ConteoInventario conteo = new ConteoInventario(
                usuario,
                tipo,
                seleccionados.size()
        );

        conteo = conteoRepository.save(conteo);

        List<DetalleConteoInventario> detallesGuardados =
                new ArrayList<>();

        // Guardar cada producto y su diferencia

        for (DetalleConteoFormulario formularioDetalle : seleccionados) {

            Producto producto = productoRepository
                    .findById(formularioDetalle.getProductoId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "Uno de los productos ya no existe."
                            )
                    );

            DetalleConteoInventario detalle =
                    new DetalleConteoInventario(
                            conteo,
                            producto.getId(),
                            producto.getCodigo(),
                            producto.getNombre(),
                            producto.getStock(),
                            formularioDetalle.getStockFisico()
                    );

            detallesGuardados.add(detalle);
        }

        detalleRepository.saveAll(detallesGuardados);

        return conteo.getId();
    }

    // CONSULTAR CONTEOS

    @Transactional(readOnly = true)
    public List<ConteoInventario> listarConteos() {

        return conteoRepository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }

    // CONSULTAR UN CONTEO

    @Transactional(readOnly = true)
    public ConteoInventario obtenerConteo(Long id) {

        return conteoRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Conteo de inventario no encontrado."
                        )
                );
    }

    // CONSULTAR DETALLES

    @Transactional(readOnly = true)
    public List<DetalleConteoInventario> listarDetalles(Long conteoId) {

        obtenerConteo(conteoId);

        return detalleRepository
                .findAllByConteo_IdOrderByNombreProductoAsc(conteoId);
    }

    // CONFIRMAR AJUSTES DEL CONTEO

    @Transactional
    public void confirmarConteo(Long conteoId) {

        ConteoInventario conteo = obtenerConteo(conteoId);

        if (!"PENDIENTE".equals(conteo.getEstado())) {

            throw new IllegalArgumentException(
                    "Este conteo ya fue confirmado."
            );
        }

        List<DetalleConteoInventario> detalles =
                listarDetalles(conteoId);

        for (DetalleConteoInventario detalle : detalles) {

            Producto producto = productoRepository
                    .findById(detalle.getProductoId())
                    .orElseThrow(() ->
                            new IllegalArgumentException(
                                    "El producto "
                                            + detalle.getNombreProducto()
                                            + " ya no existe."
                            )
                    );

            // Verificar que el stock no haya cambiado
            // desde que se registró el conteo.

            if (producto.getStock() != detalle.getStockSistema()) {

                throw new IllegalStateException(
                        "El stock del producto "
                                + producto.getNombre()
                                + " cambió después del conteo. "
                                + "Debe realizar una nueva verificación."
                );
            }

            int diferencia = detalle.getDiferencia();

            // Si hay diferencias, generar movimiento

            if (diferencia != 0) {

                MovimientoFormulario movimiento =
                        new MovimientoFormulario();

                movimiento.setProductoId(producto.getId());

                movimiento.setTipo(
                        diferencia > 0
                                ? TipoMovimiento.ENTRADA
                                : TipoMovimiento.SALIDA
                );

                movimiento.setCantidad(Math.abs(diferencia));

                movimiento.setMotivo(
                        "Ajuste por conteo físico. Conteo N.º "
                                + conteoId
                );

                // Reutilizar el servicio de movimientos.
                // Aquí también se ejecutan Factory,
                // Auditoría y Observer.

                movimientoService.registrarMovimiento(movimiento);
            }

            detalle.setAjusteAplicado(true);
        }

        detalleRepository.saveAll(detalles);

        // Finalizar la sesión

        conteo.setEstado("CONFIRMADO");

        conteoRepository.save(conteo);
    }
}
