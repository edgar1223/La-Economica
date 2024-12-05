/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
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
    private String ruta = "Inventario";
    private EmpleadoService empleadoService = new EmpleadoService();

    public String login() {
        System.out.println("entro");
        try {
            empleadoLogueado = empleadoService.login(nombre, password);
            System.out.println("entro2");

            if (empleadoLogueado == null) {
                ruta = "login";
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", "Usuario o contraseña incorrectos."));
                return null; // Permanecer en la misma página
            }

            // Guardar en sesión
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
                    .getExternalContext().getSession(true);
            session.setAttribute("empleadoLogueado", empleadoLogueado);
            ruta = "Inventario";
            return "home.xhtml?faces-redirect=true"; // Redirigir al home
        } catch (IllegalArgumentException e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error", e.getMessage()));
            return null; // Permanecer en la página de login
        }
    }

    // Getters y Setters
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

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public String irPagina() {
        return ruta;
    }
}
