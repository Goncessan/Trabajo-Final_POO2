
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CategoriaInventarioService {

    private final CategoriaInventarioRepository categoriaRepository;
    private final ProductoRepository productoRepository;
    private final AuditoriaInventarioService auditoriaService;

    public CategoriaInventarioService(
            CategoriaInventarioRepository categoriaRepository,
            ProductoRepository productoRepository,
            AuditoriaInventarioService auditoriaService) {

        this.categoriaRepository = categoriaRepository;
        this.productoRepository = productoRepository;
        this.auditoriaService = auditoriaService;
    }

    @Transactional(readOnly = true)
    public List<CategoriaInventario> listar() {
        return categoriaRepository.findAllByOrderByNombreAsc();
    }

    @Transactional(readOnly = true)
    public CategoriaInventario obtener(Long id) {
        return categoriaRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Categoría no encontrada."
                        )
                );
    }

    @Transactional
    public void guardar(CategoriaInventario datos) {

        String nombre = datos.getNombre() == null
                ? "" : datos.getNombre().trim();

        if (nombre.isBlank()) {
            throw new IllegalArgumentException(
                    "El nombre es obligatorio."
            );
        }

        if (nombre.length() > 80) {
            throw new IllegalArgumentException(
                    "El nombre no puede superar 80 caracteres."
            );
        }

        String descripcion = datos.getDescripcion() == null
                ? "" : datos.getDescripcion().trim();

        if (descripcion.length() > 250) {
            throw new IllegalArgumentException(
                    "La descripción no puede superar 250 caracteres."
            );
        }

        CategoriaInventario categoria;
        boolean edicion = datos.getId() != null;

        if (edicion) {
            categoria = obtener(datos.getId());

            if (categoriaRepository
                    .existsByNombreIgnoreCaseAndIdNot(
                            nombre, categoria.getId())) {

                throw new IllegalArgumentException(
                        "Ya existe otra categoría con ese nombre."
                );
            }

            String nombreAnterior = categoria.getNombre();

            boolean utilizada = productoRepository.findAll()
                    .stream()
                    .anyMatch(p ->
                            p.getCategoria() != null &&
                                    p.getCategoria().equalsIgnoreCase(
                                            nombreAnterior));

            if (utilizada &&
                    !nombreAnterior.equalsIgnoreCase(nombre)) {

                throw new IllegalArgumentException(
                        "No se puede cambiar el nombre porque "
                                + "hay productos en esta categoría."
                );
            }

        } else {

            if (categoriaRepository
                    .existsByNombreIgnoreCase(nombre)) {

                throw new IllegalArgumentException(
                        "Ya existe una categoría con ese nombre."
                );
            }

            categoria = new CategoriaInventario();
        }

        categoria.setNombre(nombre);
        categoria.setDescripcion(descripcion);

        categoriaRepository.save(categoria);

        auditoriaService.registrar(
                edicion ? "EDICIÓN CATEGORÍA"
                        : "REGISTRO CATEGORÍA",
                "Categoría: " + nombre,
                null
        );
    }

    @Transactional
    public void eliminar(Long id) {

        CategoriaInventario categoria = obtener(id);

        boolean utilizada = productoRepository.findAll()
                .stream()
                .anyMatch(p ->
                        p.getCategoria() != null &&
                                p.getCategoria().equalsIgnoreCase(
                                        categoria.getNombre()));

        if (utilizada) {
            throw new IllegalArgumentException(
                    "No puedes eliminar esta categoría "
                            + "porque tiene productos registrados."
            );
        }

        String nombre = categoria.getNombre();

        categoriaRepository.delete(categoria);

        auditoriaService.registrar(
                "ELIMINACIÓN CATEGORÍA",
                "Se eliminó la categoría: " + nombre,
                null
        );
    }
}
