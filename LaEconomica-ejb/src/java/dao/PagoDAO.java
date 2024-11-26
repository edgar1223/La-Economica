/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.Pago;
import proxy.DatabaseProxy;

/**
 * Data Access Object (DAO) para la entidad Pago.
 * Proporciona métodos para realizar operaciones relacionadas con la persistencia
 * de pagos en la base de datos.
 * 
 * @author Edgar
 */
public class PagoDAO {

    /**
     * Registra un nuevo pago en la base de datos.
     * 
     * @param pago La instancia de Pago que se desea registrar.
     * @throws RuntimeException Si ocurre un error durante la transacción.
     */
    public void registrarPago(Pago pago) {
        EntityManager em = null;       // Manejador de entidades.
        EntityTransaction tx = null;  // Transacción para garantizar consistencia.

        try {
            em = DatabaseProxy.getEntityManager(); // Obtiene el EntityManager del proxy.
            tx = em.getTransaction();             // Inicia la transacción.
            tx.begin();

            em.persist(pago); // Persiste la entidad Pago en la base de datos.

            tx.commit(); // Confirma la transacción.
        } catch (Exception e) {
            // Manejo de errores: realiza un rollback si la transacción está activa.
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // Lanza una excepción con información detallada del error.
            throw new RuntimeException("Error al registrar el pago: " + e.getMessage(), e);
        } finally {
            // Asegura que el EntityManager se cierre para liberar recursos.
            if (em != null) {
                em.close();
            }
        }
    }
}
