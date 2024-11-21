/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import model.Empleado;
import service.EmpleadoService;
import javax.inject.Inject;
import javax.faces.bean.ManagedBean;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import model.Sucursal;

/**
 *
 * @author Edgar
 */
@Named(value = "empleadoController")
@RequestScoped
public class EmpleadoController {

    @Inject
    private EmpleadoService empleadoService;
    private String mensaje;
    private Empleado empleado = new Empleado();
    private Integer claveEmpleado = 0;
    private List<Empleado> empleados;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        if (empleado != null) {
            System.out.println("Cargando empleado: " + empleado.getNombre());
            if(claveEmpleado==0)
                 claveEmpleado = empleado.getClave();
            System.out.print("clave" + claveEmpleado);
            System.out.print("clave" + empleado.getClave());

            this.empleado = empleado;
        } else {

            System.out.print("error empleado null");
        }
    }

    public List<Empleado> getEmpleados() {
        if (empleados == null) { // Solo carga si es nulo
            try {
                empleados = empleadoService.mostrarEmpleados();
            } catch (Exception e) {
                this.mensaje = "Error al cargar la lista de empleados: " + e.getMessage();
            }
        }
        return empleados;
    }

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

            Sucursal sucursalFicticia = new Sucursal();
            sucursalFicticia.setId(1); // Asignar ID ficticio

            empleado.setSucursal_id(sucursalFicticia);
            empleadoService.agregarEmpleado(empleado);

            // Guardar el mensaje en el contexto Flash para la redirección
            FacesContext facesContext = FacesContext.getCurrentInstance();
            ExternalContext externalContext = facesContext.getExternalContext();
            externalContext.getFlash().setKeepMessages(true);

            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "El empleado " + empleado.getNombre() + " fue agregado exitosamente.",
                            "El empleado " + empleado.getNombre() + " fue agregado exitosamente."));
            //  empleado = null;
            // Redirigir a la página de empleados
            externalContext.redirect("Empleado.xhtml"); // Resetear el formulario
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage("messages",
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo agregar el empleado."));
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void eliminarEmpleado(int clave) {
        try {
            empleadoService.eliminarEmpleado(clave);

            // Refrescar la lista de empleados
            empleados = null; // Para que se recargue en getEmpleados()

            // Mostrar mensaje de éxito
            FacesContext facesContext = FacesContext.getCurrentInstance();
            facesContext.addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado Eliminado Correctamente",
                            "El empleado con clave " + clave + " fue eliminado."));

        } catch (Exception e) {
            // Mostrar mensaje de error
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo eliminar el empleado."));
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cargarEmpleado(Empleado ep) {
        System.out.print(ep.getApellidoMaterno());
        // empleado = null;
        empleado = ep;

    }

    public void actualizarEmpleado(int id) {
        System.out.print(empleado.getApellidoMaterno());
        System.out.print(empleado.getApellidoPaterno());
            System.out.print("clave" + id);


        System.out.print(empleado.getDomicilio());
        System.out.print(empleado.getEdad());
        System.out.print(empleado.getSueldo());

        System.out.print(empleado.getTelefono());

        try {
            if (empleado == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se encontró el empleado a actualizar."));
                return;
            }
            Sucursal sucursalFicticia = new Sucursal();
            sucursalFicticia.setId(1); // Asignar ID ficticio
            empleado.setSucursal_id(sucursalFicticia);
            empleadoService.actualizarEmpleado(empleado,id);

            // Refrescar la lista de empleados
            empleados = null; // Forzar la recarga

            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Empleado actualizado", "El empleado fue actualizado correctamente."));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "No se pudo actualizar el empleado."));
            e.printStackTrace();
        }
    }

}
