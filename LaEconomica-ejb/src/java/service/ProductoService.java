/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ProductoDAO;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import model.Producto;

/**
 *
 * @author Edgar
 */
@Stateless
public class ProductoService {

    private ProductoDAO productoDAO = new ProductoDAO();

    public List<Producto> obtenerTodosLosProductos() {
        return productoDAO.findAll();
    }

    public Producto obtenerProductoPorId(int id) {
        return productoDAO.findById(id);
    }

    public void guardarProducto(Producto producto) {
        System.out.println("entro al save service");

        productoDAO.save(producto);
    }

    public void eliminarProducto(int id) {
        productoDAO.delete(id);
    }

    public void actualizarProducto(Producto producto) {
        productoDAO.update(producto);
    }

    public Producto obtenerProducto(int productoId) {
        System.out.println("Entro cargaDatosProducto " + productoId);
        return productoDAO.obtenerProductoPorId(productoId);
    }

    public List<Object[]> obtenerTopSucursalesPorProducto(int productoId) {
        System.out.println("Entro obtenerTopSucursalesPorProducto " + productoId);

        return productoDAO.obtenerTopSucursalesPorProducto(productoId);
    }

    public List<Object[]> obtenerHistorialDeVentas(int productoId) {
        return productoDAO.obtenerHistorialDeVentas(productoId);
    }

    public List<Object[]> obtenerPedidosPendientes(int productoId) {
        return productoDAO.obtenerPedidosPendientes(productoId);
    }
}
