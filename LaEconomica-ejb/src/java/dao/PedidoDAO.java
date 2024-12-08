/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Pedido;
import model.PedidoProducto;
import model.Sucursal;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.List;

import model.Pedido;

import javax.persistence.EntityManager;

import javax.persistence.EntityManager;

import model.Pedido;

import javax.persistence.EntityManager;
import proxy.DatabaseProxy;

public class PedidoDAO {

    /**
     * Agrega un nuevo pedido a la base de datos.
     *
     * @param pedido El pedido a agregar.
     */
    public Pedido agregarPedido(Pedido pedido) {
        EntityManager em = DatabaseProxy.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            em.persist(pedido);
            tx.commit();
            return pedido; // Retorna el pedido persistido
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return null; // Devuelve null si ocurre un error
        } finally {
            em.close();
        }
    }

    public HashMap<Pedido, PedidoProducto> obtenerPedidosConProductos() {
        EntityManager em = DatabaseProxy.getEntityManager();
        HashMap<Pedido, PedidoProducto> resultado = new HashMap<>();

        try {
            // Consulta para obtener los datos de Pedido y PedidoProducto
            String jpql = "SELECT pp FROM PedidoProducto pp JOIN FETCH pp.pedido";
            List<PedidoProducto> pedidoProductos = em.createQuery(jpql, PedidoProducto.class).getResultList();

            // Construir el HashMap con los resultados
            for (PedidoProducto pedidoProducto : pedidoProductos) {
                resultado.put(pedidoProducto.getPedido(), pedidoProducto);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return resultado;
    }

    /**
     * Actualiza un pedido existente en la base de datos.
     *
     * @param pedido El pedido actualizado.
     * @param id El ID del pedido a actualizar.
     */
    public void actualizarPedido(Pedido pedido, int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Pedido pedidoExistente = em.find(Pedido.class, id);
            if (pedidoExistente != null) {
                pedido.setId(id);
                em.merge(pedido);
            } else {
                System.out.println("El pedido no existe, no se puede actualizar.");
            }
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    /**
     * Lista todos los pedidos asociados a una sucursal específica.
     *
     * @param sucursalId El ID de la sucursal.
     * @return Lista de pedidos asociados a la sucursal.
     */
    public List<Pedido> listarPedidosPorSucursal(int sucursalId) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT p FROM Pedido p JOIN Sucursal s ON p.id = s.inventario WHERE s.id = :sucursalId",
                    Pedido.class)
                    .setParameter("sucursalId", sucursalId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Agrega un producto a un pedido.
     *
     * @param pedidoProducto El objeto PedidoProducto que representa el producto
     * y pedido.
     */
    public void agregarProductoAPedido(PedidoProducto pedidoProducto) {
        EntityManager em = null;
        try {
            em = DatabaseProxy.getEntityManager();
            em.getTransaction().begin(); // Iniciar la transacción

            // Verificar si el Pedido está gestionado
            Pedido pedido = em.find(Pedido.class, pedidoProducto.getPedido().getId());
            if (pedido == null) {
                // Persistir el Pedido si no existe
                em.persist(pedidoProducto.getPedido());
            } else {
                // Usar el Pedido existente
                pedidoProducto.setPedido(pedido);
            }

            // Persistir el PedidoProducto
            em.persist(pedidoProducto);
            em.getTransaction().commit(); // Confirmar la transacción
        } catch (Exception e) {
            if (em != null && em.getTransaction().isActive()) {
                em.getTransaction().rollback(); // Revertir en caso de error
            }
            e.printStackTrace(); // Log del error
            throw new RuntimeException("Error al agregar producto al pedido", e);
        } finally {
            if (em != null) {
                em.close(); // Asegurar el cierre del EntityManager
            }
        }
    }

    /**
     * Obtiene los productos asociados a un pedido específico.
     *
     * @param pedidoId El ID del pedido.
     * @return Lista de productos asociados al pedido.
     */
    public List<PedidoProducto> listarProductosDePedido(int pedidoId) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            return em.createQuery(
                    "SELECT pp FROM PedidoProducto pp WHERE pp.id.pedidoId = :pedidoId",
                    PedidoProducto.class)
                    .setParameter("pedidoId", pedidoId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Pedido findById(int id) {
        EntityManager em = DatabaseProxy.getEntityManager();
        return em.find(Pedido.class, id);
    }

    public boolean actualizarEstadoPedido(int pedidoId, String nuevoEstado) {
        EntityManager em = DatabaseProxy.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Pedido pedido = em.find(Pedido.class, pedidoId);
            if (pedido != null) {
                pedido.setEstado(nuevoEstado); // Cambiar el estado del pedido
                em.merge(pedido); // Persistir el cambio
                tx.commit();
                return true; // Retorna true si se actualizó correctamente
            } else {
                tx.rollback();
                return false; // Retorna false si no se encontró el pedido
            }
        } catch (Exception e) {
            if (tx.isActive()) {
                tx.rollback();
            }
            e.printStackTrace();
            return false; // Retorna false si ocurrió un error
        } finally {
            em.close();
        }
    }

}
