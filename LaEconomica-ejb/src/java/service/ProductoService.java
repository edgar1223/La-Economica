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
        if (producto.getPromocion() > 1) {
            producto.setPrecio_original(producto.getPrecio());
            float precio = (float) (producto.getPrecio() - (producto.getPrecio() * producto.getPromocion()));
            producto.setPrecio(precio);
            producto.setPromocion((float) (producto.getPromocion() * 100));
        } else {
            producto.setPrecio_original(producto.getPrecio());
        }
        productoDAO.save(producto);
    }

    public void eliminarProducto(int id) {
        productoDAO.delete(id);
    }

    public void actualizarProducto(Producto producto) {
        if (producto.getPromocion() > 1) {
            producto.setPrecio_original(producto.getPrecio());
            float precio = (float) (producto.getPrecio() - (producto.getPrecio() * producto.getPromocion()));
            producto.setPrecio(precio);
            producto.setPromocion((float) (producto.getPromocion() * 100));
        } else {
            producto.setPrecio(producto.getPrecio_original());
        }
        productoDAO.update(producto);
    }

    public void AplicaPromocion(Producto producto, double descuento) {
        // Convertimos el descuento al formato decimal si viene en porcentaje
        if (descuento > 1) {
            descuento = descuento / 100.0;
            producto.setPrecio_original(producto.getPrecio());
            float precio = (float) (producto.getPrecio() - (producto.getPrecio() * descuento));

            System.out.println("Precio final: " + precio);
            System.out.println("Cálculo: " + producto.getPrecio() + " - (" + producto.getPrecio() + " * " + descuento + ")");

            producto.setPrecio(precio);
            producto.setPromocion((float) (descuento * 100));

            productoDAO.update(producto);
        } else {
            producto.setPrecio(producto.getPrecio_original());
            productoDAO.update(producto);
        }

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
