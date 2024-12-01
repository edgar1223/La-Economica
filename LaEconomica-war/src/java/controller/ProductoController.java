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
 *
 * @author Edgar
 */
@Named(value = "productoController")
@SessionScoped
public class ProductoController implements Serializable {

    @EJB
    private ProductoService productoService;

    private Producto producto = new Producto();
    private List<Producto> productos;

    private List<Object[]> topSucursalesPorVentas;
    private List<Object[]> historialVentas;
    private List<Object[]> stockPorSucursal;
    private List<Object[]> pedidosPendientes;
    private double descuento;

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public List<Producto> getProductos() {
        if (productos == null) {
            cargarProducto();
        }
        return productos;
    }

    public void guardarProducto() {
        try {
            // Intenta guardar el producto
            productoService.guardarProducto(producto);

            // Muestra un mensaje de éxito si no hubo errores
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "El Producto '" + producto.getNombre() + "' ha sido registrado exitosamente."));

            // Limpia el objeto producto para un nuevo registro
            producto = new Producto();
            productos = null; // Forzar recarga de la lista
        } catch (Exception e) {
            // Maneja errores y muestra un mensaje en la interfaz
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al registrar el producto: " + e.getMessage()));
        }
        cargarProducto();
    }

    public void eliminarProducto(int id) {
        try {
            productoService.eliminarProducto(id);
            productos = null; // Forzar recarga

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "El Producto '" + id + "' ha sido eliminado exitosamente."));

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al registrar el producto: " + e.getMessage()));
        }
        cargarProducto();
    }

    public void ElimnarProductoCargado() {
        producto = new Producto();
        productos = null;
        cargarProducto();
    }

    public void actualizarProducto() {
        try {
            // Actualiza el producto en la base de datos
            productoService.actualizarProducto(producto);

            // Muestra un mensaje de éxito
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "El Producto '" + producto.getNombre() + "' ha sido actualizado exitosamente."));

            // Recargar la lista de productos
            productos = productoService.obtenerTodosLosProductos();

            // Limpia el objeto producto para evitar conflictos en la vista
            producto = new Producto();
        } catch (Exception e) {
            // Maneja errores y muestra un mensaje en la interfaz
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al actualizar el producto: " + e.getMessage()));
        }
    }

    public void cargarDatosProducto(int idProducto) {
        try {
            // Obtiene los datos relacionados con el producto
            System.out.println("Entro cargaDatosProducto " + idProducto);
            producto = productoService.obtenerProducto(idProducto);
            topSucursalesPorVentas = productoService.obtenerTopSucursalesPorProducto(idProducto);
            historialVentas = productoService.obtenerHistorialDeVentas(idProducto);

            pedidosPendientes = productoService.obtenerPedidosPendientes(idProducto);

            // Muestra un mensaje indicando que los datos fueron cargados correctamente
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Éxito",
                            "Los datos del producto fueron cargados exitosamente."));
        } catch (Exception e) {
            // Manejo de errores
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error",
                            "Ocurrió un error al cargar los datos del producto: " + e.getMessage()));
        }
    }

    public void AplicaPromocion(Producto producto, double descuento) {
        System.out.print(producto.getDescripcion());
        System.out.print(producto.getPrecio());
        productoService.AplicaPromocion(producto, descuento);

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Éxito",
                        "El Producto '" + producto.getNombre() + "' ha sido aplicado la promocion exitosamente."));
        cargarProducto();
    }

    public void cargarProducto() {
        productos = productoService.obtenerTodosLosProductos();
    }

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
