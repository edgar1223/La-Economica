/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Empleado;
import model.Pedido;
import model.PedidoProducto;
import model.Producto;
import service.PedidoService;

/**
 *
 * @author Edgar
 */
@Named(value = "pedidoControlller")
@SessionScoped
public class PedidoControlller implements Serializable {

    private Pedido pedido = new Pedido();
    private PedidoService pedidService = new PedidoService();
    private PedidoProducto pedidoProducto = new PedidoProducto();
    private List<Producto> producto;
    private boolean pedidoCreado = false;
    private int idProducto;
    private int cantidad;
    Date fechaActual = new Date();
    private HashMap<Producto, Integer> productoSeleccionado = new HashMap<>();

    /**
     * Creates a new instance of PedidoControlller
     */
    public PedidoControlller() {
    }

    public void crearPedido() {
        pedido = pedidService.agregarPedido(pedido);
        pedidoCreado = pedido != null;
        cargarProducto();

    }

    public void agregarLista() {
        for (Producto p : producto) {
            if (p.getId() == idProducto) {
                productoSeleccionado.put(p, cantidad);
        addMessage(FacesMessage.SEVERITY_INFO, "Producto agregado", "se Cargo el prodocto  '" +p.getNombre()+"'");
                break;
            }
        }
        
    }

    public void cargarProducto() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");
        producto = pedidService.obtenerProducto(empl.getSucursal_id().getId());
    }

    public void eliminarPedido() {

        pedidoCreado = false;
        idProducto = 0;
        cantidad = 0;
        pedido = new Pedido();
        productoSeleccionado = new HashMap<>();
    }

    public void agregarPedidoProducto() {
        System.out.println("Actualizar");
        // pedidoProducto.setPedido(pedido);

        pedidService.agregarProductoAPedido(pedidoProducto, productoSeleccionado, pedido.getId());
        System.out.println("Actualizar");
        productoSeleccionado = new HashMap<>();       
        pedido = new Pedido();
        pedidoCreado = false;
        productoSeleccionado = new HashMap<>();
         addMessage(FacesMessage.SEVERITY_INFO, "Pedio Exitoso", "se Realizo el pedido ");
    }

    public void addMessage(FacesMessage.Severity severity, String summary, String detail) {
        FacesContext.getCurrentInstance().
                addMessage(null, new FacesMessage(severity, summary, detail));
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public PedidoProducto getPedidoProducto() {
        return pedidoProducto;
    }

    public void setPedidoProducto(PedidoProducto pedidoProducto) {
        this.pedidoProducto = pedidoProducto;
    }

    public List<Producto> getProducto() {
        if (producto == null) {
            cargarProducto();
        }
        return producto;
    }

    public void setProducto(List<Producto> producto) {
        this.producto = producto;
    }

    public boolean isPedidoCreado() {
        return pedidoCreado;
    }

    public void setPedidoCreado(boolean pedidoCreado) {
        this.pedidoCreado = pedidoCreado;
    }

    public int getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(int idProducto) {
        this.idProducto = idProducto;
    }

    public HashMap<Producto, Integer> getProductoSeleccionado() {
        return productoSeleccionado;
    }

    public void setProductoSeleccionado(HashMap<Producto, Integer> productoSeleccionado) {
        this.productoSeleccionado = productoSeleccionado;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public Date getFechaActual() {
        return fechaActual;
    }

    public void setFechaActual(Date fechaActual) {
        this.fechaActual = fechaActual;
    }

}
