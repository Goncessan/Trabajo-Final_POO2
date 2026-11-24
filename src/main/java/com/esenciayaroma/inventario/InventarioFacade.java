
package com.esenciayaroma.inventario;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventarioFacade {

    private final InventarioService inventarioService;
    private final MovimientoInventarioService movimientoService;
    private final ReporteInventarioService reporteService;

    public InventarioFacade(
            InventarioService inventarioService,
            MovimientoInventarioService movimientoService,
            ReporteInventarioService reporteService) {

        this.inventarioService = inventarioService;
        this.movimientoService = movimientoService;
        this.reporteService = reporteService;
    }

    // 1. OBTENER RESUMEN GENERAL DEL INVENTARIO

    public ResumenInventario obtenerResumen(
            String busqueda,
            Marca marca,
            String categoria,
            boolean soloBajo) {

        return new ResumenInventario(

                inventarioService.filtrarProductos(
                        busqueda,
                        marca,
                        categoria,
                        soloBajo
                ),

                inventarioService.contarProductos(),

                inventarioService.listarCategorias()
        );
    }

    // 2. CONSULTAR PRODUCTO

    public Producto obtenerProducto(Long id) {
        return inventarioService.obtenerPorId(id);
    }

    // 3. VALIDAR CODIGOS

    public boolean existeCodigo(String codigo) {
        return inventarioService.existeCodigo(codigo);
    }

    public boolean existeOtroProductoConCodigo(
            String codigo, Long id) {

        return inventarioService
                .existeOtroProductoConCodigo(codigo, id);
    }

    // 4. ADMINISTRAR PRODUCTOS

    public void guardarProducto(Producto producto) {
        inventarioService.guardarProducto(producto);
    }

    public void actualizarProducto(
            Long id, Producto producto) {

        inventarioService.actualizarProducto(id, producto);
    }

    public boolean eliminarProducto(Long id) {
        return inventarioService.eliminarProducto(id);
    }

    // 5. ADMINISTRAR MOVIMIENTOS

    public void registrarMovimiento(
            MovimientoFormulario formulario) {

        movimientoService.registrarMovimiento(formulario);
    }

    public List<MovimientoInventario> listarMovimientos() {
        return movimientoService.listarMovimientos();
    }

    // 6. GENERAR REPORTE GENERAL

    public ReporteInventario generarReporte() {
        return reporteService.generarReporte();
    }
}
