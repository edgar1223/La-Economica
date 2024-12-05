/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.transaction.Transactional;
import model.Inventario;
import model.InventarioProducto;
import model.Producto;
import proxy.DatabaseProxy;

/**
 * Clase DAO para gestionar operaciones relacionadas con el inventario y
 * productos.
 *
 * @author Edgar
 */
public class InventarioDao {

    /**
     * Obtiene los productos de una sucursal específica basándose en su
     * inventario.
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

    /**
     * Obtiene el producto del inventario de una sucursal específica si existe.
     *
     * @param sucursalId Identificador de la sucursal.
     * @param productoId Identificador del producto.
     * @return El producto si pertenece al inventario de la sucursal, null de lo
     * contrario.
     */
    public Producto obtenerProductoDeInventario(int sucursalId, int productoId) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            String jpql = "SELECT ip.producto FROM InventarioProducto ip "
                    + "WHERE ip.producto.id = :productoId "
                    + "AND ip.inventario.id = (SELECT s.inventarioSucursal.id FROM Sucursal s WHERE s.id = :sucursalId)";
            return em.createQuery(jpql, Producto.class)
                    .setParameter("productoId", productoId)
                    .setParameter("sucursalId", sucursalId)
                    .getSingleResult(); // Busca el producto específico
        } catch (javax.persistence.NoResultException e) {
            // Retorna null si no se encuentra el producto
            return null;
        } catch (Exception e) {
            e.printStackTrace(); // Log del error
            throw new RuntimeException("Error al obtener el producto del inventario", e);
        } finally {
            if (em != null) {
                em.close(); // Asegurar el cierre del EntityManager
            }
        }
    }

    /**
     * Verifica si un producto está en el inventario de una sucursal y si la
     * cantidad solicitada está disponible.
     *
     * @param sucursalId Identificador de la sucursal.
     * @param productoId Identificador del producto.
     * @param cantidadSolicitada Cantidad solicitada del producto.
     * @return true si el producto pertenece al inventario de la sucursal y la
     * cantidad solicitada está disponible; false en caso contrario.
     */
    public boolean verificarProductoCantidadDisponible(int sucursalId, int productoId, int cantidadSolicitada) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            String jpql = "SELECT ip.productoDisponible FROM InventarioProducto ip "
                    + "WHERE ip.producto.id = :productoId "
                    + "AND ip.inventario.id = (SELECT s.inventarioSucursal.id FROM Sucursal s WHERE s.id = :sucursalId)";

            // Recupera la cantidad disponible del producto
            Integer cantidadDisponible = em.createQuery(jpql, Integer.class)
                    .setParameter("productoId", productoId)
                    .setParameter("sucursalId", sucursalId)
                    .getSingleResult();

            // Verifica si la cantidad solicitada está disponible
            return cantidadDisponible != null && cantidadSolicitada <= cantidadDisponible;
        } catch (javax.persistence.NoResultException e) {
            // Retorna false si no se encuentra el producto en el inventario
            return false;
        } catch (Exception e) {
            e.printStackTrace(); // Log del error
            throw new RuntimeException("Error al verificar la cantidad disponible del producto", e);
        } finally {
            if (em != null) {
                em.close(); // Asegurar el cierre del EntityManager
            }
        }
    }

    /**
     * Actualiza la cantidad disponible de un producto en el inventario tras
     * registrar una venta.
     *
     * @param sucursalId Identificador de la sucursal.
     * @param productoId Identificador del producto vendido.
     * @param cantidadVendida Cantidad vendida del producto.
     */
    public void actualizarCantidadDisponibleTrasVenta(int sucursalId, int productoId, int cantidadVendida) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            em.getTransaction().begin(); // Iniciar la transacción

            // Buscar el producto en el inventario de la sucursal
            String jpql = "SELECT ip FROM InventarioProducto ip "
                    + "WHERE ip.producto.id = :productoId "
                    + "AND ip.inventario.id = (SELECT s.inventarioSucursal.id FROM Sucursal s WHERE s.id = :sucursalId)";
            InventarioProducto inventarioProducto = em.createQuery(jpql, InventarioProducto.class)
                    .setParameter("productoId", productoId)
                    .setParameter("sucursalId", sucursalId)
                    .getSingleResult();

            // Reducir la cantidad disponible
            if (inventarioProducto.getProductoDisponible() >= cantidadVendida) {
                inventarioProducto.setProductoDisponible(inventarioProducto.getProductoDisponible() - cantidadVendida);
                em.merge(inventarioProducto); // Actualizar la entidad
            } else {
                throw new RuntimeException("Cantidad insuficiente en inventario para el producto con ID: " + productoId);
            }

            em.getTransaction().commit(); // Confirmar la transacción
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir en caso de error
            }
            e.printStackTrace(); // Log del error
            throw new RuntimeException("Error al actualizar la cantidad disponible del producto", e);
        } finally {
            if (em != null) {
                em.close(); // Cerrar el EntityManager
            }
        }
    }

    @Transactional
   public void crear(Inventario inventario) {
        EntityManager em = DatabaseProxy.getEntityManager();
        EntityTransaction transaction = null;

        try {
            // Iniciar transacción
            transaction = em.getTransaction();
            transaction.begin();

            // Persistir el inventario
            em.persist(inventario);

            // Confirmar transacción
            transaction.commit();
            System.out.println("Inventario insertado correctamente.");
        } catch (Exception e) {
            // Si hay un error, revertir la transacción
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            System.err.println("Error al insertar el inventario: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // Cerrar el EntityManager
            if (em != null) {
                em.close();
            }
        }
    }
}
