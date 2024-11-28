/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package proxy;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

/**
 * Clase Proxy para la gestión centralizada de la conexión con la base de datos mediante JPA.
 * Proporciona métodos para inicializar, obtener y cerrar el EntityManagerFactory y el EntityManager.
 * 
 * @author Edgar
 */
public class DatabaseProxy {

    // Atributos estáticos para la gestión del EntityManagerFactory
    private static EntityManagerFactory emf; 
    private static final Logger LOGGER = Logger.getLogger(DatabaseProxy.class.getName());

    /**
     * Constructor privado para evitar la instanciación de esta clase, 
     * ya que utiliza un patrón Singleton para la gestión del EntityManagerFactory.
     */
    private DatabaseProxy() {
    }

    /**
     * Método sincronizado para obtener un EntityManager.
     * Si el EntityManagerFactory no está inicializado, lo inicializa antes de crear el EntityManager.
     * 
     * @return un nuevo EntityManager.
     */
    public static synchronized EntityManager getEntityManager() {
        if (emf == null || !emf.isOpen()) {
            initEntityManagerFactory();
        }
        return emf.createEntityManager();
    }

    /**
     * Método privado para inicializar el EntityManagerFactory.
     * Utiliza el archivo de configuración `persistence.xml`.
     */
    private static void initEntityManagerFactory() {
        try {
            if (emf == null) {
                emf = Persistence.createEntityManagerFactory("LaEconomica-ejbPU");
                LOGGER.info("EntityManagerFactory inicializado correctamente.");
            }
        } catch (PersistenceException ex) {
            LOGGER.log(Level.SEVERE, "Error al inicializar EntityManagerFactory: {0}", ex.getMessage());
            throw new IllegalStateException("No se pudo inicializar el EntityManagerFactory.", ex);
        }
    }

    /**
     * Método sincronizado para cerrar el EntityManagerFactory.
     * Libera los recursos asociados al EntityManagerFactory si está abierto.
     */
    public static synchronized void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
            LOGGER.info("EntityManagerFactory cerrado correctamente.");
        }
    }
}
