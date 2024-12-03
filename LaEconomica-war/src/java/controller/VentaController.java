/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Empleado;
import service.VentaService;

/**
 *
 * @author Edgar
 */
@Named(value = "ventaController")
@SessionScoped
public class VentaController implements Serializable {

    private VentaService ventaService = new VentaService();
    private List<Object[]> ventasPorSucursal;
    private List<Object[]> topEmpleados;
    private List<Object[]> ventasPorMes;
    private List<Object[]> ProductosVendidos;
    private Object venta;
    private int sucursalId; // ID de la sucursal seleccionada en la vista.

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");

        this.sucursalId = empl.getSucursal_id().getId(); // ID predeterminado; puedes configurarlo desde la vista.
        cargarVentasIniciales();
    }

    /**
     * Carga inicial de datos al cargar la vista.
     */
    public void cargarVentasIniciales() {
        ventasPorSucursal = ventaService.obtenerVentasPorSucursal(sucursalId);
        topEmpleados = ventaService.obtenerTopEmpleadosPorVentas();
        ventasPorMes = ventaService.obtenerVentasPorMes(sucursalId);
    }

    /**
     * Método para recargar las ventas de una sucursal específica.
     */
    public void recargarVentasPorSucursal() {
        ventasPorSucursal = ventaService.obtenerVentasPorSucursal(sucursalId);
    }

    public String getNombreMes(int mesNumero) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        if (mesNumero >= 1 && mesNumero <= 12) {
            return meses[mesNumero - 1];
        } else {
            return "Mes inválido";
        }
    }

    public void ObtenerProducto(int id,Object ventaSelect) {
        venta=ventaSelect;
        ProductosVendidos = ventaService.obtenerProductosVendidosPorVenta(id);
    }

    /**
     * Getters y setters para que las propiedades sean accesibles en la vista
     * (XHTML).
     */
    public List<Object[]> getVentasPorSucursal() {
        return ventasPorSucursal;
    }

    public List<Object[]> getTopEmpleados() {
        return topEmpleados;
    }

    public List<Object[]> getVentasPorMes() {
        return ventasPorMes;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public List<Object[]> getProductosVendidos() {
        return ProductosVendidos;
    }

    public void setProductosVendidos(List<Object[]> ProductosVendidos) {
        this.ProductosVendidos = ProductosVendidos;
    }

    public Object getVenta() {
        return venta;
    }

    public void setVenta(Object venta) {
        this.venta = venta;
    }

    
}
