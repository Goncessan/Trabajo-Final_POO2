package com.esenciayaroma.ventas.soporte;

/**
 * Textos que ve el usuario en el módulo de ventas.
 * Son los mismos mensajes que ya existían en el código original.
 */
public final class MensajesVenta {

    // Errores de negocio
    public static final String CLIENTE_NO_EXISTE =
            "El cliente seleccionado no existe.";
    public static final String PRODUCTO_NO_EXISTE =
            "El producto seleccionado no existe.";
    public static final String ENCARGO_NO_EXISTE =
            "El encargo seleccionado no existe.";
    public static final String ENCARGO_OTRO_CLIENTE =
            "El encargo pertenece a otro cliente.";
    public static final String MARCA_DISTINTA =
            "El producto no pertenece a la marca del encargo.";
    public static final String CANTIDAD_SUPERA_PENDIENTE =
            "La cantidad supera lo pendiente del encargo.";
    public static final String PAGO_SUPERA_TOTAL =
            "El pago inicial supera el total de la venta.";
    public static final String ENCARGO_NO_ENCONTRADO = "Encargo no encontrado";
    public static final String ENCARGO_YA_ENTREGADO =
            "Este encargo ya fue entregado.";

    // Mensajes de éxito (flash)
    public static final String CLIENTE_REGISTRADO =
            "Cliente registrado correctamente.";
    public static final String ENCARGO_REGISTRADO =
            "Encargo registrado correctamente.";
    public static final String VENTA_REGISTRADA =
            "Venta registrada correctamente.";
    public static final String ALERTA_ATENDIDA =
            "Alerta marcada como atendida.";

    // Códigos de error para el formulario
    public static final String CODIGO_PEDIDO_INVALIDO = "pedido.invalido";
    public static final String CODIGO_VENTA_INVALIDA = "venta.invalida";

    // Motivo que se guarda en el movimiento de inventario
    public static final String MOTIVO_VENTA = "Venta a ";

    private MensajesVenta() {
    }
}
