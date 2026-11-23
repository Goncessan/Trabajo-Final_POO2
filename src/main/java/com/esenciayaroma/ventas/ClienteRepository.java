
package com.esenciayaroma.ventas;

import org.jspecify.annotations.NullMarked;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

@NullMarked
public interface ClienteRepository
        extends JpaRepository<Cliente, Long> {

    List<Cliente> findAllByOrderByNombreAsc();
}
