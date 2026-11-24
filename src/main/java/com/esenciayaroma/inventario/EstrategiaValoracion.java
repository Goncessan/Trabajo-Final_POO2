package com.esenciayaroma.inventario;

import java.math.BigDecimal;

public interface EstrategiaValoracion {

    BigDecimal calcularValor(Producto producto);

}
