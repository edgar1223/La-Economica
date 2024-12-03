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
 * DAO para la gestión de productos. Proporciona métodos para realizar
 * operaciones CRUD y consultas específicas sobre la entidad Producto.
 *
 * @author Edgar
 */
public class ProductoDAO {

    private EntityManager em; // Manejador de entidades para la persistencia.

    /**
     * Recupera todos los productos registrados en la base de datos.
     *
     * @return Lista de productos.
     */
    public List<Producto> findAll() {
        em = DatabaseProxy.getEntityManager();
        return em.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
    }

    /**
     * Busca un producto por su identificador único.
     *
     * @param id Identificador del producto.
     * @return El producto encontrado o null si no existe.
     */
    public Producto findById(int id) {
        em = DatabaseProxy.getEntityManager();
        return em.find(Producto.class, id);
    }

    /**
     * Guarda un nuevo producto en la base de datos.
     *
     * @param producto Objeto producto a persistir.
     */
    public void save(Producto producto) {
        EntityTransaction tx = null; // Transacción para garantizar consistencia.

        try {
            em = DatabaseProxy.getEntityManager(); // Obtiene el EntityManager del proxy.
            tx = em.getTransaction();             // Inicia la transacción.
            tx.begin();

            em.persist(producto); // Persiste la entidad Producto en la base de datos.

            tx.commit(); // Confirma la transacción.
        } catch (Exception e) {
            // Manejo de errores: realiza un rollback si la transacción está activa.
            if (tx != null && tx.isActive()) {
                tx.rollback();
            }
            // Lanza una excepción con información detallada del error.
            throw new RuntimeException("Error al registrar el producto: " + e.getMessage(), e);
        } finally {
            // Asegura que el EntityManager se cierre para liberar recursos.
            if (em != null) {
                em.close();
            }
        }
    }

    /**
     * Elimina un producto por su identificador único.
     *
     * @param id Identificador del producto a eliminar.
     */
    public void delete(int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca el producto por su identificador.
            Producto p = em.find(Producto.class, id);
            if (p != null) {
                em.getTransaction().begin();
                em.remove(p); // Elimina el producto.
                em.getTransaction().commit();
            }
        } finally {
            em.close();
        }
    }

    /**
     * Actualiza un producto existente en la base de datos.
     *
     * @param producto Objeto producto con los datos actualizados.
     */
    public void update(Producto producto) {
        EntityTransaction tx = null;
        try {
            em = DatabaseProxy.getEntityManager();
            tx = em.getTransaction();
            tx.begin();

            // Verifica si el producto existe antes de actualizar.
            Producto productoExistente = em.find(Producto.class, producto.getId());
            if (productoExistente != null) {
                em.merge(producto); // Actualiza el producto.
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

    /**
     * Obtiene un producto por su identificador y muestra información adicional.
     *
     * @param productoId Identificador del producto.
     * @return Producto encontrado o null si no existe.
     */
    public Producto obtenerProductoPorId(int productoId) {
        System.out.println("Entro cargaDatosProducto Dao " + productoId);

        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            // Busca el producto por su identificador.
            Producto p = em.find(Producto.class, productoId);
            System.out.println("Producto encontrado: " + p);
            System.out.println("Descripción del producto: " + p.getDescripcion());
            return p;
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene las sucursales con mayores ventas de un producto específico.
     *
     * @param productoId Identificador del producto.
     * @return Lista de objetos con la información de sucursales y ventas.
     */
    public List<Object[]> obtenerTopSucursalesPorProducto(int productoId) {
        System.out.println("Entro obtenerTopSucursalesPorProducto Dao " + productoId);

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
            manejarExcepcion(e, "Error al consultar top sucursales por producto");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Obtiene el historial de ventas de un producto específico.
     *
     * @param productoId Identificador del producto.
     * @return Lista de objetos con información del historial de ventas.
     */
    public List<Object[]> obtenerHistorialDeVentas(int productoId) {
        System.out.println("Entro obtenerHistorialDeVentas Dao " + productoId);

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
            manejarExcepcion(e, "Error al consultar historial de ventas");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Obtiene los pedidos pendientes relacionados con un producto.
     *
     * @param productoId Identificador del producto.
     * @return Lista de objetos con información de pedidos pendientes.
     */
    public List<Object[]> obtenerPedidosPendientes(int productoId) {
        System.out.println("Entro obtenerPedidosPendientes Dao " + productoId);

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
            manejarExcepcion(e, "Error al consultar pedidos pendientes");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Maneja excepciones lanzadas durante las operaciones con la base de datos.
     *
     * @param e Excepción lanzada.
     * @param mensaje Mensaje informativo sobre el contexto del error.
     */
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
