/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.time.LocalDate;
import model.Empleado;
import proxy.DatabaseProxy;
import javax.persistence.EntityManager;
import java.util.List;
import model.RegistroHoras;

/**
 * DAO para la gestión de empleados. Proporciona métodos para realizar 
 * operaciones CRUD sobre la entidad Empleado.
 * 
 * @author Edgar
 */
public class EmpleadoDAO {

    /**
     * Agrega un nuevo empleado a la base de datos.
     * 
     * @param empleado El empleado a agregar.
     */
    public void agregarEmpleado(Empleado empleado) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(empleado); // Inserta el empleado en la base de datos.
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene un empleado por su clave única.
     * 
     * @param clave La clave única del empleado.
     * @return El empleado encontrado o null si no existe.
     */
    public Empleado obtenerEmpleado(int clave) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca el empleado en la base de datos por su clave.
            return em.createQuery("SELECT e FROM Empleado e WHERE e.clave = :clave", Empleado.class)
                     .setParameter("clave", clave)
                     .getSingleResult();
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista de todos los empleados registrados.
     * 
     * @return Lista de empleados.
     */
    public List<Empleado> mostrarEmpleados() {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza los datos de un empleado existente.
     * 
     * @param empleado El empleado con los nuevos datos.
     * @param id       La clave del empleado a actualizar.
     */
    public void actualizarEmpleado(Empleado empleado, int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            em.getTransaction().begin();
            // Busca al empleado existente en la base de datos.
            Empleado empleadoExistente = em.find(Empleado.class, id);
            if (empleadoExistente != null) {
                // Si existe, realiza la actualización.
                empleado.setClave(id);
                em.merge(empleado);
            } else {
                System.out.println("El empleado no existe, no se puede actualizar.");
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    /**
     * Elimina un empleado de la base de datos por su clave.
     * 
     * @param clave La clave del empleado a eliminar.
     */
    public void eliminarEmpleado(int clave) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca al empleado por su clave.
            Empleado empleado = em.find(Empleado.class, clave);
            if (empleado != null) {
                em.getTransaction().begin();
                em.remove(empleado); // Elimina el empleado.
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
