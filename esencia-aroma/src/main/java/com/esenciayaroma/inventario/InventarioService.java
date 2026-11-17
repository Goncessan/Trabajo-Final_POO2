
package com.esenciayaroma.inventario;

import com.esenciayaroma.abastecimiento.RecepcionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Locale;

@Service
public class InventarioService {


    private final ProductoRepository productoRepository;
    private final MovimientoInventarioRepository movimientoRepository;
    private final RecepcionRepository recepcionRepository;

    public InventarioService(
            ProductoRepository productoRepository,
            MovimientoInventarioRepository movimientoRepository,
            RecepcionRepository recepcionRepository) {

        this.productoRepository = productoRepository;
        this.movimientoRepository = movimientoRepository;
        this.recepcionRepository = recepcionRepository;
    }

    @Transactional(readOnly = true)
    public List<Producto> listarProductos() {
        return productoRepository.findAllByOrderByNombreAsc();
    }

    // BUSQUEDAS Y FILTROS
    @Transactional(readOnly = true)
    public List<Producto> filtrarProductos(
            String busqueda,
            Marca marca,
            String categoria,
            boolean soloBajo) {

        String texto = busqueda == null
                ? ""
                : busqueda.trim().toLowerCase(Locale.ROOT);

        String categoriaFiltro = categoria == null
                ? ""
                : categoria.trim();

        return productoRepository
                .findAllByOrderByNombreAsc()
                .stream()
                .filter(p ->
                        texto.isEmpty()
                                || p.getNombre()
                                .toLowerCase(Locale.ROOT)
                                .contains(texto)
                                || p.getCodigo()
                                .toLowerCase(Locale.ROOT)
                                .contains(texto)
                )
                .filter(p ->
                        marca == null
                                || p.getMarca() == marca
                )
                .filter(p ->
                        categoriaFiltro.isEmpty()
                                || p.getCategoria()
                                .equalsIgnoreCase(categoriaFiltro)
                )
                .filter(p ->
                        !soloBajo
                                || p.getStock() <= p.getStockMinimo()
                )
                .toList();
    }

    @Transactional(readOnly = true)
    public List<String> listarCategorias() {

        return productoRepository.findAll()
                .stream()
                .map(Producto::getCategoria)
                .distinct()
                .sorted(String.CASE_INSENSITIVE_ORDER)
                .toList();
    }

    @Transactional(readOnly = true)
    public long contarProductos() {
        return productoRepository.count();
    }

    @Transactional(readOnly = true)
    public Producto obtenerPorId(Long id) {

        return productoRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(
                                HttpStatus.NOT_FOUND,
                                "Producto no encontrado"
                        )
                );
    }

    @Transactional(readOnly = true)
    public boolean existeCodigo(String codigo) {

        return productoRepository
                .existsByCodigoIgnoreCase(codigo.trim());
    }

    @Transactional(readOnly = true)
    public boolean existeOtroProductoConCodigo(
            String codigo, Long id) {

        return productoRepository
                .existsByCodigoIgnoreCaseAndIdNot(
                        codigo.trim(), id
                );
    }

    // REGISTRAR PRODUCTO Y STOCK INICIAL
    @Transactional
    public void guardarProducto(Producto producto) {

        normalizarDatos(producto);

        Producto guardado = productoRepository.save(producto);

        // El stock inicial se registra como una entrada
        if (guardado.getStock() > 0) {

            MovimientoInventario movimiento =
                    new MovimientoInventario(
                            guardado,
                            TipoMovimiento.ENTRADA,
                            guardado.getStock(),
                            0,
                            guardado.getStock(),
                            "Stock inicial al registrar el producto"
                    );

            movimientoRepository.save(movimiento);
        }
    }

    // ACTUALIZAR DATOS DEL PRODUCTO
    @Transactional
    public void actualizarProducto(
            Long id,
            Producto datosActualizados) {

        Producto producto = obtenerPorId(id);

        normalizarDatos(datosActualizados);

        producto.setCodigo(datosActualizados.getCodigo());
        producto.setNombre(datosActualizados.getNombre());
        producto.setMarca(datosActualizados.getMarca());
        producto.setCategoria(datosActualizados.getCategoria());
        producto.setPrecioCompra(datosActualizados.getPrecioCompra());
        producto.setPrecioVenta(datosActualizados.getPrecioVenta());
        producto.setStockMinimo(datosActualizados.getStockMinimo());

        // No modificamos el stock desde este formulario.
        // Las existencias se controlan mediante movimientos.

        productoRepository.save(producto);
    }

    // ELIMINAR SOLO PRODUCTOS SIN MOVIMIENTOS

    @Transactional
    public boolean eliminarProducto(Long id) {

        Producto producto = obtenerPorId(id);

        boolean tieneMovimientos =
                movimientoRepository.existsByProductoId(id);

        boolean tieneRecepciones =
                recepcionRepository.existsByDetallesProductoId(id);

        if (tieneMovimientos || tieneRecepciones) {
            return false;
        }

        productoRepository.delete(producto);

        return true;
    }

    private void normalizarDatos(Producto producto) {

        producto.setCodigo(
                producto.getCodigo()
                        .trim()
                        .toUpperCase(Locale.ROOT)
        );

        producto.setNombre(producto.getNombre().trim());
        producto.setCategoria(producto.getCategoria().trim());
    }
}
