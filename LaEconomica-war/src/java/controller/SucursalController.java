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
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Empleado;
import model.Sucursal;
import service.InventarioService;
import service.SucursalService;
import service.VentaService;

/**
 *
 * @author Edgar
 */
@Named(value = "sucursalController")
@SessionScoped
public class SucursalController implements Serializable {

    private List<Sucursal> sucursales;
    private Sucursal sucursal = new Sucursal();
    private int sucursalId;
    private List<Object[]> topEmpleados;
    private List<Object[]> ventasPorMes;
    private List<Object[]> productoVendidos;
    private InventarioService iventarioService = new InventarioService();
    private VentaService ventaService = new VentaService();

    private SucursalService sucursalService = new SucursalService();

    @PostConstruct
    public void init() {
        listarSucursales();
        sucursal = new Sucursal();
    }

    /**
     * Carga inicial de datos al cargar la vista.
     */
    public void cargarDatosSucursal(int id) {
        sucursalId = id;
        sucursal = sucursalService.obtenerSucursalPorId(sucursalId);
        topEmpleados = ventaService.obtenerTopEmpleadosPorVentas();
        ventasPorMes = ventaService.obtenerVentasPorMes(sucursalId);
        productoVendidos = iventarioService.obtenerProductosMasVendidos(sucursalId);

    }

    public void listarSucursales() {
        sucursales = sucursalService.listarSucursales();
    }

    public void crearSucursal(Empleado E) {
        if (sucursal == null) {
            System.out.println("Error: Sucursal es nulo antes de crear.");
        }
        System.out.println("N o es nulo");

        sucursal.setEmpleados(E.getClave());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Éxito",
                        "La sucursal '" + sucursal.getDescripcion() + "' ha sido registrado exitosamente."));
        sucursalService.crearSucursal(sucursal);
        listarSucursales();
        sucursal = new Sucursal();
    }

    public void editarSucursal() {
        sucursalService.editarSucursal(sucursal);
        listarSucursales();
    }

    public void eliminarSucursal(int id) {
        sucursalService.eliminarSucursal(id);
         FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Éxito",
                        "La sucursal '" +id + "' ha sido Eliminada exitosamente."));
        listarSucursales();
    }

    public void actualizar() {
        sucursalService.actualizarSucursal(sucursal);
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Éxito",
                        "La sucursal '" + sucursal.getDescripcion() + "' ha sido actualizada exitosamente."));
    }

    public void cargarSucursalPorId() {
        sucursal = sucursalService.obtenerSucursalPorId(sucursalId);
    }

    public void eliminarSucursalCargado() {
        sucursal = new Sucursal();
    }

    // Getters y Setters
    public List<Sucursal> getSucursales() {
        return sucursales;
    }

    public Sucursal getSucursal() {
        return sucursal;
    }

    public void setSucursal(Sucursal sucursal) {
        this.sucursal = sucursal;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public List<Object[]> getTopEmpleados() {
        return topEmpleados;
    }

    public void setTopEmpleados(List<Object[]> topEmpleados) {
        this.topEmpleados = topEmpleados;
    }

    public List<Object[]> getVentasPorMes() {
        return ventasPorMes;
    }

    public void setVentasPorMes(List<Object[]> ventasPorMes) {
        this.ventasPorMes = ventasPorMes;
    }

    public List<Object[]> getProductoVendidos() {
        return productoVendidos;
    }

    public void setProductoVendidos(List<Object[]> productoVendidos) {
        this.productoVendidos = productoVendidos;
    }

    public InventarioService getIventarioService() {
        return iventarioService;
    }

    public void setIventarioService(InventarioService iventarioService) {
        this.iventarioService = iventarioService;
    }

}
