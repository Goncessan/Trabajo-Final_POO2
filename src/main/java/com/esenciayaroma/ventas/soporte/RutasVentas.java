package com.esenciayaroma.ventas.soporte;

/**
 * Rutas web y nombres de vistas del módulo de ventas.
 */
public final class RutasVentas {

    // Rutas
    public static final String BASE = "/ventas";
    public static final String CLIENTES = "/clientes";
    public static final String PEDIDOS = "/pedidos";
    public static final String REGISTRAR = "/registrar";
    public static final String HISTORIAL = "/historial";
    public static final String ALERTAS = "/alertas";
    public static final String ATENDER_ALERTA = "/{id}/atender";

    // Vistas (plantillas Thymeleaf)
    public static final String VISTA_PANEL = "ventas";
    public static final String VISTA_CLIENTES = "clientes";
    public static final String VISTA_PEDIDOS = "pedidos-clientes";
    public static final String VISTA_VENTA = "venta-form";
    public static final String VISTA_HISTORIAL = "ventas-historial";
    public static final String VISTA_ALERTAS = "alertas-stock";

    // Redirecciones
    public static final String REDIRIGIR_CLIENTES = "redirect:/ventas/clientes";
    public static final String REDIRIGIR_PEDIDOS = "redirect:/ventas/pedidos";
    public static final String REDIRIGIR_HISTORIAL = "redirect:/ventas/historial";
    public static final String REDIRIGIR_ALERTAS = "redirect:/ventas/alertas";

    private RutasVentas() {
    }
}
