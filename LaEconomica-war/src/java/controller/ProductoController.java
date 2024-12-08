/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Producto;
import service.ProductoService;

/**
 * Controlador encargado de la gestión de productos.
 * Maneja las operaciones CRUD y otras funcionalidades relacionadas con los productos.
 * Utiliza un contexto de sesión para mantener la información del producto cargado.
 * 
 * @author Edgar
 */
@Named(value = "productoController")
@SessionScoped
public class ProductoController implements Serializable {

    @EJB
    private ProductoService productoService; // Servicio de productos que interactúa con la capa de datos.

    private Producto producto = new Producto(); // Objeto Producto usado en la vista para operaciones.
    private List<Producto> productos; // Lista de productos para mostrar en la vista.

    private List<Object[]> topSucursalesPorVentas; // Lista con las sucursales top por ventas de un producto.
    private List<Object[]> historialVentas; // Historial de ventas de un producto.
    private List<Object[]> stockPorSucursal; // Información del stock por sucursal.
    private List<Object[]> pedidosPendientes; // Pedidos pendientes para un producto.
    private double descuento; // Descuento aplicado a un producto.

    // Getters y Setters
    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        if (productos == null) {
            cargarProducto(); // Carga la lista de productos si aún no se ha inicializado.
        }
        return productos;
    }

    /**
     * Guarda un producto en la base de datos.
     * Muestra un mensaje de éxito o error según el resultado de la operación.
     */
    public void guardarProducto() {
        try {
            productoService.guardarProducto(producto); // Llama al servicio para guardar el producto.
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "El Producto '" + producto.getNombre() + "' ha sido registrado exitosamente."));
            producto = new Producto(); // Limpia el objeto producto para un nuevo registro.
            productos = null; // Forzar recarga de la lista.
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al registrar el producto: " + e.getMessage()));
        }
        cargarProducto(); // Recarga la lista de productos.
    }

    /**
     * Elimina un producto de la base de datos.
     * @param id Identificador del producto a eliminar.
     */
    public void eliminarProducto(int id) {
        try {
            productoService.eliminarProducto(id); // Llama al servicio para eliminar el producto.
            productos = null; // Forzar recarga de la lista.
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "El Producto '" + id + "' ha sido eliminado exitosamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al eliminar el producto: " + e.getMessage()));
        }
        cargarProducto(); // Recarga la lista de productos.
    }

    /**
     * Limpia el producto cargado y recarga la lista de productos.
     */
    public void ElimnarProductoCargado() {
        producto = new Producto(); // Resetea el producto cargado.
        productos = null; // Forzar recarga.
        cargarProducto();
    }

    /**
     * Actualiza un producto en la base de datos.
     * Muestra un mensaje de éxito o error según el resultado.
     */
    public void actualizarProducto() {
        try {
            productoService.actualizarProducto(producto); // Llama al servicio para actualizar el producto.
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "El Producto '" + producto.getNombre() + "' ha sido actualizado exitosamente."));
            productos = productoService.obtenerTodosLosProductos(); // Recarga la lista de productos.
            cargarProducto();
            producto = new Producto(); // Limpia el objeto producto para evitar conflictos en la vista.
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al actualizar el producto: " + e.getMessage()));
        }
    }

    /**
     * Carga los datos de un producto específico, incluyendo información adicional como historial de ventas.
     * @param idProducto Identificador del producto a cargar.
     */
    public void cargarDatosProducto(int idProducto) {
        try {
            producto = productoService.obtenerProducto(idProducto); // Carga los datos del producto.
            topSucursalesPorVentas = productoService.obtenerTopSucursalesPorProducto(idProducto); // Carga las sucursales top.
            historialVentas = productoService.obtenerHistorialDeVentas(idProducto); // Carga el historial de ventas.
            pedidosPendientes = productoService.obtenerPedidosPendientes(idProducto); // Carga los pedidos pendientes.
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "Los datos del producto fueron cargados exitosamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al cargar los datos del producto: " + e.getMessage()));
        }
    }

    /**
     * Aplica una promoción a un producto específico.
     * @param producto Producto al cual aplicar la promoción.
     * @param descuento Descuento a aplicar.
     */
    public void AplicaPromocion(Producto producto, double descuento) {
        productoService.AplicaPromocion(producto, descuento); // Llama al servicio para aplicar la promoción.
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Éxito",
                        "El Producto '" + producto.getNombre() + "' ha sido aplicado la promoción exitosamente."));
        cargarProducto(); // Recarga la lista de productos.
    }

    /**
     * Carga todos los productos disponibles.
     */
    public void cargarProducto() {
        productos = productoService.obtenerTodosLosProductos(); // Carga la lista de productos desde el servicio.
    }

    // Getters y Setters adicionales
    public ProductoService getProductoService() {
        return productoService;
    }

    public void setProductoService(ProductoService productoService) {
        this.productoService = productoService;
    }

    public List<Object[]> getTopSucursalesPorVentas() {
        return topSucursalesPorVentas;
    }

    public void setTopSucursalesPorVentas(List<Object[]> topSucursalesPorVentas) {
        this.topSucursalesPorVentas = topSucursalesPorVentas;
    }

    public List<Object[]> getHistorialVentas() {
        return historialVentas;
    }

    public void setHistorialVentas(List<Object[]> historialVentas) {
        this.historialVentas = historialVentas;
    }

    public List<Object[]> getStockPorSucursal() {
        return stockPorSucursal;
    }

    public void setStockPorSucursal(List<Object[]> stockPorSucursal) {
        this.stockPorSucursal = stockPorSucursal;
    }

    public List<Object[]> getPedidosPendientes() {
        return pedidosPendientes;
    }

    public void setPedidosPendientes(List<Object[]> pedidosPendientes) {
        this.pedidosPendientes = pedidosPendientes;
    }

    public double getDescuento() {
        return descuento;
    }

    public void setDescuento(double descuento) {
        this.descuento = descuento;
    }

}
