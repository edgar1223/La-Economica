/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.ProductoDAO;
import java.util.List;
import javax.ejb.Stateless;
import model.Producto;

/**
 * Clase de servicio para la gestión de productos.
 * Proporciona métodos para realizar operaciones CRUD sobre productos,
 * así como aplicar promociones y obtener datos relacionados con ventas y pedidos.
 * 
 * @author Edgar
 */
@Stateless
public class ProductoService {

    private ProductoDAO productoDAO = new ProductoDAO();

    /**
     * Obtiene la lista de todos los productos registrados en el sistema.
     * 
     * @return Lista de productos.
     */
    public List<Producto> obtenerTodosLosProductos() {
        return productoDAO.findAll();
    }

    /**
     * Obtiene un producto específico por su ID.
     * 
     * @param id ID del producto a obtener.
     * @return Objeto Producto correspondiente al ID.
     */
    public Producto obtenerProductoPorId(int id) {
        return productoDAO.findById(id);
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     * Si tiene una promoción, calcula el precio final y actualiza los datos del producto.
     * 
     * @param producto Producto a guardar.
     */
    public void guardarProducto(Producto producto) {
        System.out.println("Entro al save service");
        if (producto.getPromocion() > 1) {
             float descueto=producto.getPromocion()/100;
            producto.setPrecio_original(producto.getPrecio());
           float precio = (float) (producto.getPrecio() - (producto.getPrecio() * descueto));
            producto.setPrecio(precio);
            producto.setPromocion((float) (producto.getPromocion() * 100));
        } else {
            producto.setPrecio_original(producto.getPrecio());
        }
        productoDAO.save(producto);
    }

    /**
     * Elimina un producto de la base de datos por su ID.
     * 
     * @param id ID del producto a eliminar.
     */
    public void eliminarProducto(int id) {
        productoDAO.delete(id);
    }

    /**
     * Actualiza la información de un producto existente.
     * Si tiene una promoción, recalcula el precio final y lo actualiza.
     * 
     * @param producto Producto con los datos actualizados.
     */
    public void actualizarProducto(Producto producto) {
        if (producto.getPromocion() > 1) {
            float descueto=producto.getPromocion()/100;
            producto.setPrecio_original(producto.getPrecio());
           float precio = (float) (producto.getPrecio() - (producto.getPrecio() * descueto));
            producto.setPrecio(precio);
            producto.setPromocion((float) (producto.getPromocion() * 100));
            productoDAO.update(producto);
        } else {
                   productoDAO.update(producto);

            
        }
        
    }

    /**
     * Aplica una promoción a un producto, actualizando su precio según el descuento proporcionado.
     * 
     * @param producto Producto al que se aplicará la promoción.
     * @param descuento Descuento a aplicar (en formato decimal o porcentaje).
     */
    public void AplicaPromocion(Producto producto, double descuento) {
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

    /**
     * Obtiene un producto específico por su ID mediante un método alternativo del DAO.
     * 
     * @param productoId ID del producto a obtener.
     * @return Producto correspondiente al ID proporcionado.
     */
    public Producto obtenerProducto(int productoId) {
        System.out.println("Entro cargaDatosProducto " + productoId);
        return productoDAO.obtenerProductoPorId(productoId);
    }

    /**
     * Obtiene las sucursales con mayor cantidad de ventas para un producto específico.
     * 
     * @param productoId ID del producto para el que se consultará el top de sucursales.
     * @return Lista de datos de las sucursales con mayor cantidad de ventas.
     */
    public List<Object[]> obtenerTopSucursalesPorProducto(int productoId) {
        System.out.println("Entro obtenerTopSucursalesPorProducto " + productoId);
        return productoDAO.obtenerTopSucursalesPorProducto(productoId);
    }

    /**
     * Obtiene el historial de ventas de un producto específico.
     * 
     * @param productoId ID del producto cuyo historial se desea consultar.
     * @return Lista de datos de las ventas del producto.
     */
    public List<Object[]> obtenerHistorialDeVentas(int productoId) {
        return productoDAO.obtenerHistorialDeVentas(productoId);
    }

    /**
     * Obtiene los pedidos pendientes de un producto específico.
     * 
     * @param productoId ID del producto para el que se consultarán los pedidos pendientes.
     * @return Lista de datos de los pedidos pendientes.
     */
    public List<Object[]> obtenerPedidosPendientes(int productoId) {
        return productoDAO.obtenerPedidosPendientes(productoId);
    }
}
