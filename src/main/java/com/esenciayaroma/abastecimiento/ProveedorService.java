package com.esenciayaroma.abastecimiento;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProveedorService {

    private final ProveedorRepository proveedorRepository;

    public ProveedorService(ProveedorRepository proveedorRepository) {
        this.proveedorRepository = proveedorRepository;
    }

    // Listar proveedores activos
    public List<Proveedor> listarProveedores() {
        return proveedorRepository.findByActivoTrue();
    }

    // Registrar proveedor
    @Transactional
    public Proveedor registrarProveedor(ProveedorFormulario formulario) {

        if (proveedorRepository.existsByNombreIgnoreCase(
                formulario.getNombre().trim())) {
            throw new IllegalArgumentException(
                    "El proveedor ya está registrado."
            );
        }

        Proveedor proveedor = new Proveedor(
                formulario.getNombre().trim(),
                formulario.getTelefono(),
                formulario.getCorreo(),
                formulario.getDireccion(),
                formulario.getTipoProveedor()
        );

        return proveedorRepository.save(proveedor);
    }

    // Buscar proveedor por ID
    public Proveedor buscarProveedor(Long id) {
        return proveedorRepository.findById(id)
                .orElseThrow(() ->
                        new IllegalArgumentException(
                                "Proveedor no encontrado."
                        )
                );
    }

    // Desactivar proveedor
    @Transactional
    public void desactivarProveedor(Long id) {
        Proveedor proveedor = buscarProveedor(id);
        proveedor.setActivo(false);
        proveedorRepository.save(proveedor);
    }
}
