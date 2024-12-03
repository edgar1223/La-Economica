/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import model.Inventario;
import model.InventarioProducto;
import model.Producto;
import proxy.DatabaseProxy;

/**
 * Clase DAO para gestionar operaciones relacionadas con el inventario y productos.
 *
 * @author Edgar
 */
public class InventarioDao {

    /**
     * Obtiene los productos de una sucursal específica basándose en su inventario.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Lista de productos en el inventario de la sucursal.
     */
    public List<InventarioProducto> obtenerProductosPorSucursal(int sucursalId) {
        EntityManager em = null;
        em = DatabaseProxy.getEntityManager();
        String jpql = "SELECT ip FROM InventarioProducto ip "
                + "WHERE ip.inventario.id = "
                + "(SELECT s.inventarioSucursal.id FROM Sucursal s WHERE s.id = :sucursalId)";
        return em.createQuery(jpql, InventarioProducto.class)
                .setParameter("sucursalId", sucursalId)
                .getResultList();
    }

    /**
     * Agrega un producto al inventario.
     *
     * @param inventarioProducto Producto a agregar al inventario.
     */
    public void agregarProductoAlInventario(InventarioProducto inventarioProducto) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            em.getTransaction().begin(); // Iniciar la transacción
            em.persist(inventarioProducto); // Guardar el producto en la base de datos
            em.getTransaction().commit(); // Confirmar la transacción
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir en caso de error
            }
            e.printStackTrace(); // Log del error
            throw new RuntimeException("Error al agregar producto al inventario", e);
        } finally {
            if (em != null) {
                em.close(); // Asegurar el cierre del EntityManager
            }
        }
    }

    /**
     * Obtiene los productos más vendidos del inventario, limitados al top 5.
     *
     * @param inventarioId Identificador del inventario.
     * @return Lista de productos más vendidos con su cantidad total vendida.
     */
    public List<Object[]> obtenerProductosMasVendidos(int inventarioId) {
        EntityManager em = null;
        em = DatabaseProxy.getEntityManager();
        String jpql = "SELECT vp.producto, SUM(vp.cantidadVendida) AS totalVendida "
                + "FROM VentaProducto vp "
                + "WHERE vp.producto.id IN "
                + "(SELECT ip.producto.id FROM InventarioProducto ip WHERE ip.inventario.id = :inventarioId) "
                + "GROUP BY vp.producto "
                + "HAVING SUM(vp.cantidadVendida) > 50 "
                + "ORDER BY totalVendida DESC";
        return em.createQuery(jpql)
                .setParameter("inventarioId", inventarioId)
                .setMaxResults(5) // Limitar el resultado a los 5 más vendidos
                .getResultList();
    }

    /**
     * Obtiene los productos menos vendidos del inventario, limitados al top 5.
     *
     * @param inventarioId Identificador del inventario.
     * @return Lista de productos menos vendidos con su cantidad total vendida.
     */
    public List<Object[]> obtenerProductosMenosVendidos(int inventarioId) {
        EntityManager em = null;
        em = DatabaseProxy.getEntityManager();
        String jpql = "SELECT vp.producto, SUM(vp.cantidadVendida) AS totalVendida "
                + "FROM VentaProducto vp "
                + "WHERE vp.producto.id IN "
                + "(SELECT ip.producto.id FROM InventarioProducto ip WHERE ip.inventario.id = :inventarioId) "
                + "GROUP BY vp.producto "
                + "HAVING SUM(vp.cantidadVendida) < 50 "
                + "ORDER BY totalVendida ASC";
        return em.createQuery(jpql)
                .setParameter("inventarioId", inventarioId)
                .setMaxResults(5) // Limitar el resultado a los 5 menos vendidos
                .getResultList();
    }

    /**
     * Actualiza un producto en el inventario.
     *
     * @param inventarioProducto Producto con los datos actualizados.
     */
    public void actualizarProductoEnInventario(InventarioProducto inventarioProducto) {
        EntityManager em = null;
        System.out.println("actualizarProductoEnInventario " + inventarioProducto.getId());

        try {
            em = DatabaseProxy.getEntityManager();
            em.getTransaction().begin(); // Iniciar la transacción
            em.merge(inventarioProducto); // Actualizar la entidad
            em.getTransaction().commit(); // Confirmar la transacción
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir en caso de error
            }
            System.out.println("Error " + e.getMessage());
            throw e; // Re-lanzar la excepción
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
    }

    /**
     * Obtiene los productos que no están en el inventario de una sucursal.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Lista de productos que no están en el inventario.
     */
    public List<Producto> obtenerProductosNoEnInventarioPorSucursal(int sucursalId) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            String jpql = "SELECT p FROM Producto p WHERE p.id NOT IN "
                    + "(SELECT ip.producto.id FROM InventarioProducto ip WHERE ip.inventario.id = "
                    + "(SELECT s.inventarioSucursal.id FROM Sucursal s WHERE s.id = :sucursalId))";
            return em.createQuery(jpql, Producto.class)
                    .setParameter("sucursalId", sucursalId)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace(); // Log del error
            throw e;
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
    }

    /**
     * Obtiene el inventario asociado a una sucursal específica.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Inventario de la sucursal.
     */
    public Inventario obtenerInventarioPorSucursal(int sucursalId) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            String jpql = "SELECT i FROM Inventario i "
                    + "WHERE i.id = (SELECT s.inventarioSucursal.id FROM Sucursal s WHERE s.id = :sucursalId)";
            return em.createQuery(jpql, Inventario.class)
                    .setParameter("sucursalId", sucursalId)
                    .getSingleResult(); // Usamos getSingleResult porque esperamos un solo inventario
        } catch (Exception e) {
            e.printStackTrace(); // Log del error
            throw e; // Re-lanzar la excepción para manejarla en la capa superior.
        } finally {
            if (em != null) {
                em.close(); // Asegurarse de cerrar el EntityManager.
            }
        }
    }

}
