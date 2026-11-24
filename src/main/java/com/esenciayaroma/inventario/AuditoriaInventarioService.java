
package com.esenciayaroma.inventario;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuditoriaInventarioService {

    private final AuditoriaInventarioRepository repository;

    public AuditoriaInventarioService(
            AuditoriaInventarioRepository repository) {

        this.repository = repository;
    }

    @Transactional
    public void registrar(
            String accion,
            String descripcion,
            String codigoProducto) {

        Authentication autenticacion =
                SecurityContextHolder.getContext()
                        .getAuthentication();

        String usuario = "Sistema";

        if (autenticacion != null
                && autenticacion.isAuthenticated()
                && !"anonymousUser".equals(
                autenticacion.getName())) {

            usuario = autenticacion.getName();
        }

        AuditoriaInventario registro =
                new AuditoriaInventario(
                        usuario,
                        accion,
                        "Inventario",
                        descripcion,
                        codigoProducto
                );

        repository.save(registro);
    }

    @Transactional(readOnly = true)
    public List<AuditoriaInventario> listarRegistros() {

        return repository
                .findAllByOrderByFechaRegistroDescIdDesc();
    }
}
