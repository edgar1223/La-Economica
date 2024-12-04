/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Empleado;
import model.Producto;
import model.Sucursal;
import model.Venta;
import model.VentaProducto;
import model.VentaProductoId;
import proxy.DatabaseProxy;

/**
 * DAO para la gestión de ventas. Proporciona métodos para consultas específicas
 * relacionadas con ventas.
 */
public class VentaDAO {

    private EntityManager em;

    /**
     * Obtiene una lista de todas las ventas asociadas a una sucursal
     * específica.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Lista de objetos con información de ventas (ID, fecha, total).
     */
    public List<Object[]> obtenerVentasPorSucursal(int sucursalId) {
        System.out.println("Consultando ventas asociadas a la sucursal con ID: " + sucursalId);

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT v.id, v.fecha, SUM(vp.cantidadVendida * vp.precioUnitario) AS total "
                    + "FROM VentaProducto vp "
                    + "JOIN vp.venta v "
                    + "WHERE v.sucursal.id = :sucursalId "
                    + "GROUP BY v.id, v.fecha "
                    + "ORDER BY v.fecha DESC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("sucursalId", sucursalId);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Ventas encontradas: " + resultados.size());
        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar ventas por sucursal");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Obtiene una lista de empleados que más ventas realizaron, ordenados por
     * el dinero generado.
     *
     * @return Lista de empleados con su total de dinero generado por ventas.
     */
    public List<Object[]> obtenerTopEmpleadosPorVentas() {
        System.out.println("Consultando empleados que más ventas realizaron.");

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT e.clave, e.nombre, SUM(vp.cantidadVendida * vp.precioUnitario) AS totalDinero "
                    + "FROM VentaProducto vp "
                    + "JOIN vp.venta v "
                    + "JOIN v.empleado e "
                    + "GROUP BY e.clave, e.nombre "
                    + "ORDER BY totalDinero DESC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Empleados encontrados: " + resultados.size());
        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar top empleados por ventas");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Obtiene las ventas de una sucursal agrupadas por mes y calcula el total
     * generado en cada mes.
     *
     * @param sucursalId Identificador de la sucursal.
     * @return Lista con la suma de ventas por mes.
     */
    public List<Object[]> obtenerVentasPorMes(int sucursalId) {
        System.out.println("Consultando ventas por mes para la sucursal con ID: " + sucursalId);

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT FUNCTION('MONTH', v.fecha) AS mes, SUM(vp.cantidadVendida * vp.precioUnitario) AS totalMes "
                    + "FROM VentaProducto vp "
                    + "JOIN vp.venta v "
                    + "WHERE v.sucursal.id = :sucursalId "
                    + "GROUP BY FUNCTION('MONTH', v.fecha) "
                    + "ORDER BY mes ASC";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("sucursalId", sucursalId);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Ventas por mes encontradas: " + resultados.size());
        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar ventas por mes");
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

    public List<Object[]> obtenerProductosVendidosPorVenta(int ventaId) {
        System.out.println("Consultando productos vendidos para la venta con ID: " + ventaId);

        em = DatabaseProxy.getEntityManager();
        List<Object[]> resultados = new ArrayList<>();
        try {
            String jpql = "SELECT p.nombre, vp.precioUnitario, vp.cantidadVendida, "
                    + "(vp.cantidadVendida * vp.precioUnitario) AS total "
                    + "FROM VentaProducto vp "
                    + "JOIN vp.producto p "
                    + "WHERE vp.venta.id = :ventaId";
            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("ventaId", ventaId);
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Productos encontrados: " + resultados.size());
        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar productos vendidos por venta");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }



        /**
         * Crea una nueva venta y le asocia productos con cantidades y precios.
         *
         * @param empleado El empleado que realiza la venta.
         * @param sucursal La sucursal donde se realiza la venta.
         * @param productosConDetalles Un mapa donde la clave es el producto y
         * el valor es un arreglo con dos elementos: [cantidadVendida,
         * precioUnitario].
         * @param descuento El descuento aplicado a la venta (puede ser null si
         * no hay descuento).
         * @return La venta creada con los productos asociados.
         */
        public Venta crearVenta(Empleado empleado, Sucursal sucursal, Map<Producto, float[]> productosConDetalles, Float descuento) {
            EntityManager em = null;

            try {
                em = DatabaseProxy.getEntityManager();
                em.getTransaction().begin();

                // Crear y persistir la venta
                Venta nuevaVenta = new Venta();
                nuevaVenta.setEmpleado(empleado);
                nuevaVenta.setSucursal(sucursal);
                nuevaVenta.setFecha(new Date());
                nuevaVenta.setDescuento(descuento);
                em.persist(nuevaVenta);

                // Asociar productos a la venta
                for (Map.Entry<Producto, float[]> entry : productosConDetalles.entrySet()) {
                    Producto producto = entry.getKey();
                    float[] detalles = entry.getValue(); // [cantidadVendida, precioUnitario]
                    int cantidadVendida = (int) detalles[0];
                    float precioUnitario = detalles[1];

                    VentaProducto ventaProducto = new VentaProducto();
                    VentaProductoId id = new VentaProductoId();
                    id.setVentaId(nuevaVenta.getId());
                    id.setProductoId(producto.getId());
                    ventaProducto.setId(id);

                    ventaProducto.setVenta(nuevaVenta);
                    ventaProducto.setProducto(producto);
                    ventaProducto.setCantidadVendida(cantidadVendida);
                    ventaProducto.setPrecioUnitario(precioUnitario);

                    em.persist(ventaProducto);
                }

                em.getTransaction().commit();
                return nuevaVenta;
            } catch (Exception e) {
                if (em != null && em.getTransaction().isActive()) {
                    em.getTransaction().rollback(); // Revertir en caso de error
                }
                e.printStackTrace(); // Log del error
                throw new RuntimeException("Error al crear la venta", e);
            } finally {
                if (em != null) {
                    em.close(); // Cerrar el EntityManager
                }
            }
        }
    

}
