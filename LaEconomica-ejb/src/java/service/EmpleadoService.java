/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.EmpleadoDAO;
import model.Empleado;
import model.Pago;
import model.RegistroHoras;
import model.Venta;
import proxy.DatabaseProxy;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import javax.ejb.Stateless;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.mindrot.jbcrypt.BCrypt;

/**
 * Clase de servicio para gestionar las operaciones relacionadas con empleados.
 * Proporciona métodos para agregar, actualizar, eliminar y consultar datos de
 * empleados.
 *
 * @author Edgar
 */
@Stateless
public class EmpleadoService {

    // Dependencias
    private EmpleadoDAO empleadoDAO = new EmpleadoDAO();
    private EntityManager entityManager;

    // Métodos CRUD básicos para empleados
    /**
     * Agrega un nuevo empleado al sistema.
     *
     * @param empleado Objeto Empleado con los datos a registrar.
     */
    public void agregarEmpleado(Empleado empleado) {
        // Validar que el password no sea nulo o vacío
        System.out.print("enttro a servicios agregar usuarios "+empleado.getPassword());
        if (empleado.getPassword() != null && !empleado.getPassword().isEmpty()) {
            // Encriptar el password
            String hashedPassword = BCrypt.hashpw(empleado.getPassword(), BCrypt.gensalt());
            empleado.setPassword(hashedPassword);
        }

        empleadoDAO.agregarEmpleado(empleado);
    }

    /**
     * Actualiza la jornada laboral de un empleado.
     *
     * @param empleado Objeto Empleado a modificar.
     * @param nuevaJornada Nueva jornada laboral en formato Date.
     * @param id Identificador del empleado.
     */
    public void cambiarJornada(Empleado empleado, Date nuevaJornada, int id) {
        if (nuevaJornada != null) {
            LocalDate localDate = nuevaJornada.toLocalDate();
            empleado.setJornadaLaboral(localDate);
        }
        empleadoDAO.actualizarEmpleado(empleado, id);
    }

    /**
     * Cambia el número de teléfono de un empleado.
     *
     * @param empleado Objeto Empleado a modificar.
     * @param nuevoTelefono Nuevo número de teléfono.
     * @param id Identificador del empleado.
     */
    public void cambiarTelefono(Empleado empleado, int nuevoTelefono, int id) {
        empleado.setTelefono(nuevoTelefono);
        empleadoDAO.actualizarEmpleado(empleado, id);
    }

    /**
     * Muestra la lista de empleados registrados.
     *
     * @return Lista de empleados.
     */
    public List<Empleado> mostrarEmpleados() {
        return empleadoDAO.mostrarEmpleados();
    }

    /**
     * Elimina un empleado por su clave única.
     *
     * @param clave Clave del empleado.
     * @return true si la operación fue exitosa.
     */
    public boolean eliminarEmpleado(int clave) {
        empleadoDAO.eliminarEmpleado(clave);
        return true;
    }

    /**
     * Actualiza la información de un empleado.
     *
     * @param empleado Objeto Empleado con los datos actualizados.
     * @param id Identificador del empleado.
     */
    public void actualizarEmpleado(Empleado empleado, int id) {
        empleadoDAO.actualizarEmpleado(empleado, id);
    }

    /**
     * Obtiene un empleado por su ID.
     *
     * @param clave Identificador del empleado.
     * @return Objeto Empleado correspondiente.
     */
    public Empleado obtenerEmpleadoPorId(Integer clave) {
        return empleadoDAO.obtenerEmpleado(clave);
    }

    // Métodos relacionados con pagos y ventas
    /**
     * Obtiene los pagos realizados a un empleado.
     *
     * @param clave Identificador del empleado.
     * @return Lista de objetos Pago asociados al empleado.
     */
    public List<Pago> obtenerPagosPorEmpleado(Integer clave) {
        entityManager = DatabaseProxy.getEntityManager();
        String queryStr = "SELECT p FROM Pago p WHERE p.empleado.clave = :clave";
        Query query = entityManager.createQuery(queryStr, Pago.class);
        query.setParameter("clave", clave);
        return query.getResultList();
    }

    /**
     * Obtiene las ventas realizadas por un empleado.
     *
     * @param clave Identificador del empleado.
     * @return Lista de objetos Venta asociados al empleado.
     */
    public List<Venta> obtenerVentasPorEmpleado(Integer clave) {
        entityManager = DatabaseProxy.getEntityManager();
        String queryStr = "SELECT v FROM Venta v WHERE v.empleado.clave = :clave";
        Query query = entityManager.createQuery(queryStr, Venta.class);
        query.setParameter("clave", clave);
        return query.getResultList();
    }

    // Métodos relacionados con el cálculo de horas trabajadas y sueldo
    /**
     * Simula el cálculo de horas trabajadas de un empleado.
     *
     * @param empleado Objeto Empleado.
     * @return Horas trabajadas estimadas.
     */
    public double calcularHorasTrabajadas(Empleado empleado) {
        return 8.0; // Ejemplo: 8 horas diarias.
    }

    /**
     * Calcula y actualiza el sueldo de un empleado en función de sus horas
     * trabajadas.
     *
     * @param empleado Objeto Empleado.
     * @param id Identificador del empleado.
     * @return Sueldo calculado.
     */
    public float calcularSueldo(Empleado empleado, int id) {
        float sueldo = (float) (calcularHorasTrabajadas(empleado) * 100); // Ejemplo: $100 por hora.
        empleado.setSueldo(sueldo);
        empleadoDAO.actualizarEmpleado(empleado, id);
        return sueldo;
    }

    /**
     * Obtiene las horas trabajadas por un empleado en el último mes.
     *
     * @param clave Identificador del empleado.
     * @return Lista de registros de horas trabajadas.
     */
    public List<RegistroHoras> obtenerHorasTrabajadasUltimoMes(Integer clave) {
        entityManager = DatabaseProxy.getEntityManager();
        LocalDate inicioMes = LocalDate.now().withDayOfMonth(1);
        LocalDate finMes = LocalDate.now();

        String queryStr = "SELECT rh FROM RegistroHoras rh "
                + "WHERE rh.empleado_clave.clave = :clave "
                + "AND rh.fecha BETWEEN :inicio AND :fin";
        Query query = entityManager.createQuery(queryStr, RegistroHoras.class);
        query.setParameter("clave", clave);
        query.setParameter("inicio", java.sql.Date.valueOf(inicioMes));
        query.setParameter("fin", java.sql.Date.valueOf(finMes));
        return query.getResultList();
    }

    public Empleado login(String nombre, String password) {
        System.out.println("entro al login service");

        if (nombre == null || password == null || nombre.isEmpty() || password.isEmpty()) {
            throw new IllegalArgumentException("El usuario y la contraseña no pueden estar vacíos.");
        }

        // Buscar el empleado por nombre
        List<Empleado> empleadoLogeado = empleadoDAO.findByNombre(nombre);
        Empleado emple=new Empleado();
        System.out.println("Entró al login: " + empleadoLogeado);

// Verifica si la lista no está vacía
        if (empleadoLogeado != null && !empleadoLogeado.isEmpty()) {
            // Itera sobre la lista
            for (Empleado em : empleadoLogeado) {
                // Verifica si la contraseña ingresada coincide con el hash almacenado
                if (BCrypt.checkpw(password, em.getPassword())) {
                    System.out.println("Usuario autenticado: " + em);
                    emple=em;
                    // Aquí puedes retornar el usuario o ejecutar lógica adicional
                } else {
                    System.out.println("Contraseña incorrecta para el usuario: " + em.getNombre());
                }
            }
        } else {
            System.out.println("No se encontraron empleados con el nombre: " + nombre);
        }
        return emple;
    }
}
