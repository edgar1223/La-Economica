/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EmpleadoDAO;
import model.Empleado;

import java.util.Date;
import java.util.List;
/**
 *
 * @author Edgar
 */
public class EmpleadoService {
     private EmpleadoDAO empleadoDAO = new EmpleadoDAO();

    public void agregarEmpleado(Empleado empleado) {
        empleadoDAO.agregarEmpleado(empleado);
    }

    public void cambiarJornada(Empleado empleado, Date nuevaJornada) {
        empleado.setJornadaLaboral((java.sql.Date) nuevaJornada);
        empleadoDAO.actualizarEmpleado(empleado);
    }

    public void cambiarTelefono(Empleado empleado, int nuevoTelefono) {
        empleado.setTelefono(nuevoTelefono);
        empleadoDAO.actualizarEmpleado(empleado);
    }

    public double calcularHorasTrabajadas(Empleado empleado) {
        // Simulación de cálculo de horas trabajadas
        return 8.0; // 8 horas por día como ejemplo.
    }

    public float calcularSueldo(Empleado empleado) {
        float sueldo = (float) (calcularHorasTrabajadas(empleado) * 100); // Sueldo base de ejemplo
        empleado.setSueldo(sueldo);
        empleadoDAO.actualizarEmpleado(empleado);
        return sueldo;
    }

    public List<Empleado> mostrarEmpleados() {
        return empleadoDAO.mostrarEmpleados();
    }
}
