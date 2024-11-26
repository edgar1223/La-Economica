/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Empleado;
import service.EmpleadoService;

/**
 *
 * @author Edgar
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

     private String nombre;
    private String password;
    private Empleado empleadoLogueado;

    
    private EmpleadoService empleadoService;

    public String login() {
        empleadoService=new EmpleadoService();
        System.out.println("entro al login");
        try {
            empleadoLogueado = empleadoService.login(nombre, password);
      //  System.out.println(empleadoLogueado.getSucursal_id());

            // Guardar el empleado en la sesión
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
            session.setAttribute("empleadoLogueado", empleadoLogueado);

            return "home.xhtml?faces-redirect=true"; // Redirigir a la página principal
        } catch (IllegalArgumentException e) {
                    System.out.println("error"+e.getMessage());

            FacesContext.getCurrentInstance().addMessage(null,
                new javax.faces.application.FacesMessage(e.getMessage()));
            return null; // Permanecer en la página de login
        }
    }

    // Getters y setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Empleado getEmpleadoLogueado() {
        return empleadoLogueado;
    }

    public void setEmpleadoLogueado(Empleado empleadoLogueado) {
        this.empleadoLogueado = empleadoLogueado;
    }

    public EmpleadoService getEmpleadoService() {
        return empleadoService;
    }

    public void setEmpleadoService(EmpleadoService empleadoService) {
        this.empleadoService = empleadoService;
    }
    
}
