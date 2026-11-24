
package com.esenciayaroma.inventario;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface DetalleConteoInventarioRepository
        extends JpaRepository<DetalleConteoInventario, Long> {

    List<DetalleConteoInventario>
    findAllByConteo_IdOrderByNombreProductoAsc(Long conteoId);
}
