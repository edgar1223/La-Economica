/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EmpleadoDAO;
import model.Empleado;
import java.sql.Date;
import java.time.LocalDate;

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

   public void cambiarJornada(Empleado empleado, Date nuevaJornada,int id) {
    // Convertir java.sql.Date a LocalDate
    if (nuevaJornada != null) {
        LocalDate localDate = nuevaJornada.toLocalDate();
        empleado.setJornadaLaboral(localDate);
    }
    empleadoDAO.actualizarEmpleado(empleado,id);
}

    public void cambiarTelefono(Empleado empleado, int nuevoTelefono,int id) {
        empleado.setTelefono(nuevoTelefono);
        empleadoDAO.actualizarEmpleado(empleado,id);
    }

    public double calcularHorasTrabajadas(Empleado empleado) {
        // Simulación de cálculo de horas trabajadas
        return 8.0; // 8 horas por día como ejemplo.
    }

    public float calcularSueldo(Empleado empleado,int id) {
        float sueldo = (float) (calcularHorasTrabajadas(empleado) * 100); // Sueldo base de ejemplo
        empleado.setSueldo(sueldo);
        empleadoDAO.actualizarEmpleado(empleado,id);
        return sueldo;
    }

    public List<Empleado> mostrarEmpleados() {
        return empleadoDAO.mostrarEmpleados();
    }
    public boolean eliminarEmpleado(int clave){
        empleadoDAO.eliminarEmpleado(clave);
        return true;
    }
    public void actualizarEmpleado(Empleado empleado,int id) {
        empleadoDAO.actualizarEmpleado(empleado,id);
    }
}
