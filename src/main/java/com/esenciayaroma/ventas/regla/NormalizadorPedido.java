package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.ventas.PedidoClienteFormulario;
import org.springframework.stereotype.Component;

/**
 * Limpia los textos del formulario de encargo.
 */
@Component
public class NormalizadorPedido {

    public String codigoCatalogo(PedidoClienteFormulario formulario) {
        return formulario.getCodigoCatalogo().trim();
    }

    public String nombreProducto(PedidoClienteFormulario formulario) {
        return formulario.getNombreProducto().trim();
    }
}
