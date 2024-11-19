/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.inject.Named;
import javax.enterprise.context.Dependent;
import javax.enterprise.context.RequestScoped;
import model.Empleado;
import service.EmpleadoService;
import javax.inject.Inject;
import javax.faces.bean.ManagedBean;
import java.util.List;

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
    private List<Empleado> empleados;

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public void agregarEmpleado() {
        empleadoService.agregarEmpleado(empleado);
        // Limpiar el objeto empleado para que el formulario quede en blanco
        this.empleado = new Empleado();
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
}
