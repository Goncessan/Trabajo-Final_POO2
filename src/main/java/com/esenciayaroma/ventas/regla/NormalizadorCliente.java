package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.ventas.Cliente;
import org.springframework.stereotype.Component;

/**
 * Quita espacios sobrantes de los datos del cliente.
 */
@Component
public class NormalizadorCliente {

    public void normalizar(Cliente cliente) {

        cliente.setNombre(cliente.getNombre().trim());

        if (cliente.getTelefono() != null) {
            cliente.setTelefono(cliente.getTelefono().trim());
        }

        if (cliente.getDireccion() != null) {
            cliente.setDireccion(cliente.getDireccion().trim());
        }
    }
}
