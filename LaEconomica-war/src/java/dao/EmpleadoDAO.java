/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import model.Empleado;
import proxy.DatabaseProxy;
import javax.persistence.EntityManager;
import java.util.List;
import model.RegistroHoras;

/**
 *
 * @author Edgar
 */
public class EmpleadoDAO {

    public void agregarEmpleado(Empleado empleado) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(empleado);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public Empleado obtenerEmpleado(int clave) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            return em.find(Empleado.class, clave);
        } finally {
            em.close();
        }
    }

    public List<Empleado> mostrarEmpleados() {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {

            System.out.print(em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList());
            System.out.print(em.createQuery("SELECT e FROM RegistroHoras e", RegistroHoras.class).getResultList());

            return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        } finally {
            em.close();
        }
    }

    public void actualizarEmpleado(Empleado empleado) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(empleado);
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }

    public void eliminarEmpleado(int clave) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            Empleado empleado = em.find(Empleado.class, clave);
            if (empleado != null) {
                em.getTransaction().begin();
                em.remove(empleado);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
}
