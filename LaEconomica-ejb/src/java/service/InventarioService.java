/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.InventarioDao;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import model.Inventario;
import model.InventarioProducto;
import model.Producto;

/**
 * Clase de servicio para la gestión de inventarios. Proporciona métodos para
 * interactuar con el DAO de inventarios. Permite operaciones como obtener
 * productos por sucursal, agregar productos al inventario y consultar productos
 * más o menos vendidos.
 *
 * @author Edgar
 */
@Stateless
public class InventarioService {

    private InventarioDao inventarioDao = new InventarioDao();

    /**
     * Obtiene la lista de productos asociados a un inventario en una sucursal
     * específica.
     *
     * @param sucursalId ID de la sucursal cuyos productos se desean obtener.
     * @return Lista de productos en el inventario de la sucursal.
     */
    public List<InventarioProducto> obtenerProductosPorSucursal(int sucursalId) {
        return inventarioDao.obtenerProductosPorSucursal(sucursalId);
    }

    /**
     * Agrega un producto al inventario de una sucursal específica.
     *
     * @param inventarioProducto Objeto del producto a agregar al inventario.
     * @param sucursalId ID de la sucursal donde se agregará el producto.
     */
    public void agregarProductoAlInventario(InventarioProducto inventarioProducto, int sucursalId) {
        try {
            // Obtiene el inventario asociado a la sucursal.
            Inventario inventario = inventarioDao.obtenerInventarioPorSucursal(sucursalId);

            // Asocia el producto con el inventario obtenido.
            inventarioProducto.setInventario(inventario);

            // Agrega el producto al inventario mediante el DAO.
            inventarioDao.agregarProductoAlInventario(inventarioProducto);
        } catch (Exception e) {
            e.printStackTrace(); // Log del error.
            throw new RuntimeException("Error al agregar el producto al inventario de la sucursal", e);
        }
    }

    /**
     * Obtiene los productos más vendidos de un inventario específico.
     *
     * @param inventarioId ID del inventario para el que se consultarán los
     * productos más vendidos.
     * @return Lista de objetos con datos de los productos más vendidos.
     */
    public List<Object[]> obtenerProductosMasVendidos(int inventarioId) {
        return inventarioDao.obtenerProductosMasVendidos(inventarioId);
    }

    /**
     * Obtiene los productos menos vendidos de un inventario específico.
     *
     * @param inventarioId ID del inventario para el que se consultarán los
     * productos menos vendidos.
     * @return Lista de objetos con datos de los productos menos vendidos.
     */
    public List<Object[]> obtenerProductosMenosVendidos(int inventarioId) {
        return inventarioDao.obtenerProductosMenosVendidos(inventarioId);
    }

    /**
     * Actualiza los datos de un producto en el inventario.
     *
     * @param inventarioProducto Objeto con los datos actualizados del producto.
     */
    public void actualizarIventarioProducto(InventarioProducto inventarioProducto) {
        System.out.println("Actualizado " + inventarioProducto.getId());

        // Actualiza el producto en el inventario mediante el DAO.
        inventarioDao.actualizarProductoEnInventario(inventarioProducto);
        System.out.println("actualizarIventarioProducto " + inventarioProducto.getId());
    }

    /**
     * Obtiene la lista de productos que no están en el inventario de una
     * sucursal específica.
     *
     * @param sucursalId ID de la sucursal para la que se consultarán los
     * productos no presentes en su inventario.
     * @return Lista de productos no presentes en el inventario de la sucursal.
     */
    public List<Producto> obtenerProductosNoEnInventarioPorSucursal(int sucursalId) {
        return inventarioDao.obtenerProductosNoEnInventarioPorSucursal(sucursalId);
    }

    public Producto verificarProductoEnInventario(int sucursalId, int productoId) {
        return inventarioDao.obtenerProductoDeInventario(sucursalId, productoId);
    }

    public boolean verificarProductoCantidadDisponible(int sucursalId, int productoId, int cantidadSolicitada) {
        return inventarioDao.verificarProductoCantidadDisponible(sucursalId, productoId, cantidadSolicitada);
    }

   
}
