/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import java.io.Serializable;
import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import model.Empleado;
import model.RegistroHoras;
import model.Sucursal;
import model.Venta;
import service.EmpleadoService;

import javax.faces.context.ExternalContext;
import javax.servlet.http.HttpSession;
import model.Pago;

/**
 * Controlador para la gestión de empleados. Maneja operaciones como agregar,
 * eliminar, actualizar y visualizar detalles de empleados.
 *
 * @author Edgar
 */
@Named(value = "empleadoController")
@SessionScoped
public class EmpleadoController implements Serializable {

    // Variables y dependencias
    private EmpleadoService empleadoService;
    private String mensaje;
    private Empleado empleado = new Empleado();
    private Integer claveEmpleado = 0;
    private List<Empleado> empleados;
    private Empleado empleadoSeleccionado = new Empleado();
    private List<Pago> listaPagos;
    private List<Venta> listaVentas;
    private List<RegistroHoras> listaHoras;
    private LoginController loginController = new LoginController();

    // Métodos getters y setters
    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        if (empleado != null) {
            System.out.println("Cargando empleado: " + empleado.getNombre());
            if (claveEmpleado == 0) {
                claveEmpleado = empleado.getClave();
            }
            this.empleado = empleado;
        } else {
            System.out.println("Error: empleado es null");
        }
    }

    public List<Empleado> getEmpleados() {
        if (empleados == null) {
            try {
                empleadoService = new EmpleadoService();
                empleados = empleadoService.mostrarEmpleados();
            } catch (Exception e) {
                mensaje = "Error al cargar la lista de empleados: " + e.getMessage();
            }
        }
        return empleados;
    }

    // Operaciones principales
    /**
     * Agrega un nuevo empleado con validaciones básicas.
     */
    public void agregarEmpleado() {
        try {
            if (empleado.getEdad() < 18 || empleado.getEdad() > 150) {
                FacesContext.getCurrentInstance().addMessage("messages",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación fallida", "La edad debe estar entre 18 y 150 años."));
                return;
            }
            if (empleado.getSueldo() <= 207) {
                FacesContext.getCurrentInstance().addMessage("messages",
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Validación fallida", "El sueldo debe ser mayor a 207."));
                return;
            }

            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");
            Sucursal sucursalFicticia = new Sucursal();
            sucursalFicticia.setId(empl.getSucursal_id().getId()); // ID ficticio para la sucursal
            empleado.setSucursal_id(sucursalFicticia);

            empleadoService = new EmpleadoService();
            empleadoService.agregarEmpleado(empleado);
            empleados = empleadoService.mostrarEmpleados();

            FacesMessage messages = new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado agregado exitosamente.",
                    "Pago registrado correctamente para el empleado con clave: " + empleado.getClave());
            FacesContext.getCurrentInstance().addMessage(null, messages);

        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el empleado."));
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Elimina un empleado dado su clave.
     *
     * @param clave Clave del empleado a eliminar.
     */
    public void eliminarEmpleado(int clave) {
        try {
            empleadoService = new EmpleadoService();
            empleadoService.eliminarEmpleado(clave);
            empleados = null; // Recargar lista

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Empleado eliminado", "El empleado con clave " + clave + " fue eliminado."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el empleado."));
            System.out.println("Error: " + e.getMessage());
        }
    }

    /**
     * Carga un empleado para su visualización o edición.
     *
     * @param ep Empleado a cargar.
     */
    public void cargarEmpleado(Empleado ep) {
        empleado = ep;
    }

    /**
     * Actualiza los datos de un empleado.
     *
     * @param id ID del empleado a actualizar.
     */
    public void actualizarEmpleado(int id) {
        try {
            if (empleado == null) {
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                        FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el empleado a actualizar."));
                return;
            }
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");
            Sucursal sucursalFicticia = new Sucursal();
            sucursalFicticia.setId(empl.getSucursal_id().getId()); // ID ficticio para la sucursal
            empleado.setSucursal_id(sucursalFicticia);

            empleadoService = new EmpleadoService();
            empleadoService.actualizarEmpleado(empleado, id);

            empleados = null; // Recargar lista
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_INFO, "Empleado actualizado", "El empleado fue actualizado correctamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el empleado."));
            e.printStackTrace();
        }
    }

    /**
     * Muestra los detalles del empleado seleccionado.
     *
     * @param clave Clave del empleado.
     */
    public void verDetallesEmpleado(Integer clave) {
        empleadoService = new EmpleadoService();
        empleadoSeleccionado = empleadoService.obtenerEmpleadoPorId(clave);
        listaPagos = empleadoService.obtenerPagosPorEmpleado(clave);
        listaVentas = empleadoService.obtenerVentasPorEmpleado(clave);
        listaHoras = empleadoService.obtenerHorasTrabajadasUltimoMes(clave);
    }

    /**
     * Metodo para eliminar al Empleado, para que no se carguen al al registrar
     * usuario
     *
     */
    public void EliminarEmpleadoCargado() {
        empleado = new Empleado();;
    }

    // Getters y setters para datos relacionados
    public Empleado getEmpleadoSeleccionado() {
        return empleadoSeleccionado;
    }

    public void setEmpleadoSeleccionado(Empleado empleadoSeleccionado) {
        this.empleadoSeleccionado = empleadoSeleccionado;
    }

    public List<Pago> getListaPagos() {
        return listaPagos;
    }

    public void setListaPagos(List<Pago> listaPagos) {
        this.listaPagos = listaPagos;
    }

    public List<Venta> getListaVentas() {
        return listaVentas;
    }

    public void setListaVentas(List<Venta> listaVentas) {
        this.listaVentas = listaVentas;
    }

    public List<RegistroHoras> getListaHoras() {
        return listaHoras;
    }

    public void setListaHoras(List<RegistroHoras> listaHoras) {
        this.listaHoras = listaHoras;
    }

}
