package com.esenciayaroma.ventas.regla;

import com.esenciayaroma.ventas.PedidoCliente;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Deja solo los encargos a los que todavía les faltan unidades.
 */
@Component
public class FiltroPedidosPendientes {

    public List<PedidoCliente> filtrar(List<PedidoCliente> pedidos) {
        return pedidos.stream()
                .filter(p -> p.getCantidadPendiente() > 0)
                .toList();
    }
}
