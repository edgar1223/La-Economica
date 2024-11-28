/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import model.Producto;
import proxy.DatabaseProxy;

/**
 *
 * @author Edgar
 */
public class ProductoDAO {

    private EntityManager em;

    public List<Producto> findAll() {
        em = DatabaseProxy.getEntityManager();
        return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    public Producto findById(int id) {
        em = DatabaseProxy.getEntityManager();
        return em.find(Producto.class, id);
    }

    public void save(Producto producto) {

        EntityTransaction tx = null;  // Transacción para garantizar consistencia.

        try {
            em = DatabaseProxy.getEntityManager(); // Obtiene el EntityManager del proxy.
            tx = em.getTransaction();             // Inicia la transacción.
            tx.begin();

            em.persist(producto); // Persiste la entidad Pago en la base de datos.

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

    public void delete(int id) {

        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca al empleado por su clave.
            Producto p = em.find(Producto.class, id);
            if (p != null) {
                em.getTransaction().begin();
                em.remove(p); // Elimina el empleado.
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    public void update(Producto producto) {
        EntityTransaction tx = null;
        try {
            em = DatabaseProxy.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Verifica si el producto existe antes de actualizar
            Producto productoExistente = em.find(Producto.class, producto.getId());
            if (productoExistente != null) {
                em.merge(producto); // Actualiza el producto
            } else {
                throw new RuntimeException("Producto con ID " + producto.getId() + " no encontrado.");
            }

            tx.commit();
        } catch (Exception e) {
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            throw new RuntimeException("Error al actualizar el producto: " + e.getMessage(), e);
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    // Datos del producto
    public Producto obtenerProductoPorId(int productoId) {
        System.out.println("Entro cargaDatosProducto Dao" + productoId);

        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca al empleado por su clave.
            Producto p = em.find(Producto.class, productoId);
            System.out.println("Entro cargaDatosProducto dao 2" + p);
            System.out.println("Entro cargaDatosProducto dao 2" + p.getDescripcion());
            return p;
        } finally {
            em.close();
        }

    }

    // Top sucursales por ventas
    public List<Object[]> obtenerTopSucursalesPorProducto(int productoId) {
        System.out.println("Entro obtenerTopSucursalesPorProducto dao" + productoId);

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT s.id, s.descripcion, SUM(vp.cantidadVendida) AS totalVentas, "
                    + "SUM(vp.cantidadVendida * vp.precioUnitario) AS totalDinero "
                    + "FROM VentaProducto vp "
                    + "JOIN vp.venta v "
                    + "JOIN v.sucursal s "
                    + "WHERE vp.producto.id = :productoId "
                    + "GROUP BY s.id, s.descripcion "
                    + "ORDER BY totalVentas DESC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("productoId", productoId);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados.size());

        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar gastos de los últimos meses");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    // Historial de ventas
    public List<Object[]> obtenerHistorialDeVentas(int productoId) {
        System.out.println("Entro obtenerTopSucursalesPorProducto dao" + productoId);

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT v.fecha, s.descripcion, vp.cantidadVendida, "
                    + "(vp.cantidadVendida * vp.precioUnitario) AS totalDinero "
                    + "FROM VentaProducto vp "
                    + "JOIN vp.venta v "
                    + "JOIN v.sucursal s "
                    + "WHERE vp.producto.id = :productoId "
                    + "ORDER BY v.fecha DESC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("productoId", productoId);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados.size());

        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar gastos de los últimos meses");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;

    }

    // Pedidos pendientes
    public List<Object[]> obtenerPedidosPendientes(int productoId) {

        System.out.println("Entro obtenerTopSucursalesPorProducto dao" + productoId);

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT pp.pedido.id, pp.cantidadPedido, p.fechaSolicitud, p.estado "
                    + "FROM PedidoProducto pp "
                    + "JOIN pp.pedido p "
                    + "WHERE pp.producto.id = :productoId AND p.estado = 'Pendiente'";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("productoId", productoId);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados.size());

        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar gastos de los últimos meses");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;

    }

    private void manejarExcepcion(Exception e, String mensaje) {
        System.err.println(mensaje + ": " + e.getMessage());
        e.printStackTrace();
    }

    /**
     * Cierra el EntityManager de manera segura.
     *
     * @param em Instancia de EntityManager a cerrar.
     */
    private void cerrarEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
            System.out.println("EntityManager cerrado.");
        }
    }
}
