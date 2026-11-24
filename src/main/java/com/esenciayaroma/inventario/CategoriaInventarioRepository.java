
package com.esenciayaroma.inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaInventarioRepository
        extends JpaRepository<CategoriaInventario, Long> {

    List<CategoriaInventario> findAllByOrderByNombreAsc();

    boolean existsByNombreIgnoreCase(String nombre);

    boolean existsByNombreIgnoreCaseAndIdNot(
            String nombre, Long id);
}
