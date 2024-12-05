/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.InventarioDao;
import dao.VentaDAO;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Empleado;
import model.InventarioProducto;
import model.Producto;
import model.Sucursal;
import model.Venta;

/**
 * Clase de servicio para gestionar las operaciones relacionadas con ventas.
 *
 * @author Edgar
 */
@Stateless

public class VentaService {

    private final VentaDAO ventaDAO;
    private InventarioDao inventarioDao = new InventarioDao();

    /**
     * Constructor de VentaService. Inicializa el DAO de ventas.
     */
    public VentaService() {
        this.ventaDAO = new VentaDAO();
    }

    /**
     * Obtiene una lista de todas las ventas asociadas a una sucursal
     * específica.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Lista de ventas con ID, fecha y total generado.
     */
    public List<Object[]> obtenerVentasPorSucursal(int sucursalId) {
        System.out.println("Servicio: Obteniendo ventas para la sucursal con ID: " + sucursalId);
        return ventaDAO.obtenerVentasPorSucursal(sucursalId);
    }

    /**
     * Obtiene una lista de empleados que más ventas realizaron, ordenados por
     * el dinero generado.
     *
     * @return Lista de empleados con su total de dinero generado por ventas.
     */
    public List<Object[]> obtenerTopEmpleadosPorVentas() {
        System.out.println("Servicio: Obteniendo top de empleados por ventas.");
        return ventaDAO.obtenerTopEmpleadosPorVentas();
    }

    /**
     * Obtiene las ventas de una sucursal agrupadas por mes y calcula el total
     * generado en cada mes.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Lista con la suma de ventas por mes.
     */
    public List<Object[]> obtenerVentasPorMes(int sucursalId) {
        System.out.println("Servicio: Obteniendo ventas por mes para la sucursal con ID: " + sucursalId);
        return ventaDAO.obtenerVentasPorMes(sucursalId);
    }

    /**
     * Obtiene los productos vendidos en una venta específica.
     *
     * @param ventaId Identificador de la venta.
     * @return Lista de productos vendidos con nombre, precio, cantidad vendida
     * y total.
     */
    public List<Object[]> obtenerProductosVendidosPorVenta(int ventaId) {
        System.out.println("Servicio: Obteniendo productos vendidos para la venta con ID: " + ventaId);
        return ventaDAO.obtenerProductosVendidosPorVenta(ventaId);
    }

    public void crearVenta(Empleado empleado, Sucursal sucursal, Map<Producto, float[]> productosConDetalles, Float descuento) {
        ventaDAO.crearVenta(empleado, sucursal, productosConDetalles, descuento);
        productosConDetalles.forEach((producto, detalles) -> {
            if (detalles != null && detalles.length > 0) {
                int sucursalId = sucursal.getId(); // Obtener el ID de la sucursal
                int productoId = producto.getId(); // Obtener el ID del producto
                int cantidadVendida = Math.round(detalles[0]); // Asumimos que la cantidad vendida es el primer elemento

                // Llamar al método para actualizar el inventario
                InventarioProducto in = inventarioDao.actualizarCantidadDisponibleTrasVenta(sucursalId, productoId, cantidadVendida);
                if (in.getProductoDisponible() <= in.getCantidadMinima()) {
                    if(in.getProductoDisponible()==0)
                    {
                         FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia: El stock del producto'"+producto.getNombre()+"' es 0", null));
                    }else{
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: El stock del producto'"+producto.getNombre()+"' es bajo", null));
                    }
                }
            }
        });

    }
}
