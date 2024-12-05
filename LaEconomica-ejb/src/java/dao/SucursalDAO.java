/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import model.Empleado;
import model.Sucursal;
import proxy.DatabaseProxy;

/**
 *
 * @author Edgar
 */
public class SucursalDAO {

    @Transactional
    public void crear(Sucursal sucursal) {
        EntityManager em = DatabaseProxy.getEntityManager();
        EntityTransaction transaction = null;

        try {
            // Iniciar transacción
            transaction = em.getTransaction();
            transaction.begin();

            // Persistir la sucursal
            em.persist(sucursal);

            // Confirmar transacción
            transaction.commit();
            System.out.println("Sucursal insertada correctamente.");
        } catch (Exception e) {
            // Si hay un error, revertir la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al insertar la sucursal: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el EntityManager
            if (em != null) {
                em.close();
            }
        }
    }

     public void delete(int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca el producto por su identificador.
            Sucursal p = em.find(Sucursal.class, id);
            if (p != null) {
                em.getTransaction().begin();
                em.remove(p); // Elimina el producto.
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }
    

    public Sucursal obtenerPorId(int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        return em.find(Sucursal.class, id);
    }

    public List<Sucursal> listarTodos() {
        EntityManager em = DatabaseProxy.getEntityManager();
        return em.createQuery("SELECT s FROM Sucursal s", Sucursal.class).getResultList();
    }

    /**
     * Actualiza los datos de un empleado existente.
     *
     * @param empleado El empleado con los nuevos datos.
     * @param id La clave del empleado a actualizar.
     */
    public void actualizar(Sucursal s, int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            em.getTransaction().begin();
            // Busca al empleado existente en la base de datos.
            Sucursal SucursalExistente = em.find(Sucursal.class, id);
            if (SucursalExistente != null) {
                // Si existe, realiza la actualización.
                s.setId(id);
                em.merge(s);
            } else {
                System.out.println("El empleado no existe, no se puede actualizar.");
            }
            em.getTransaction().commit();
        } finally {
            em.close();
        }
    }
}
