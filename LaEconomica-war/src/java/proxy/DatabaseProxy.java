/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package proxy;

import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Edgar
 */
public class DatabaseProxy {
    private static EntityManagerFactory emf;
    private static final Logger LOGGER = Logger.getLogger(DatabaseProxy.class.getName());

    private DatabaseProxy() {
    }

    public static synchronized EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            initEntityManagerFactory();
        }
        return emf.createEntityManager();
    }

    private static void initEntityManagerFactory() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("LaEconomica-warPU");
                LOGGER.info("EntityManagerFactory inicializado correctamente.");
            }
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Error al inicializar EntityManagerFactory: {0}", ex.getMessage());
            throw new IllegalStateException("No se pudo inicializar el EntityManagerFactory.", ex);
        }
    }

    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            LOGGER.info("EntityManagerFactory cerrado correctamente.");
        }
    }
}
