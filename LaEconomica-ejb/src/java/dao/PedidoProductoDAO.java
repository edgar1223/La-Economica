/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import model.PedidoProducto;
import model.PedidoProductoId;
import proxy.DatabaseProxy;

/**
 *
 * @author Edgar
 */
public class PedidoProductoDAO {

    public PedidoProducto findById(PedidoProductoId id) {
        EntityManager entityManager = DatabaseProxy.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        return entityManager.find(PedidoProducto.class, id);
    }

    public List<PedidoProducto> findAll() {
        EntityManager entityManager = DatabaseProxy.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        return entityManager.createQuery("FROM PedidoProducto", PedidoProducto.class).getResultList();
    }

    public PedidoProducto save(PedidoProducto pedidoProducto) {
        EntityManager entityManager = DatabaseProxy.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        if (entityManager.contains(pedidoProducto)) {
            pedidoProducto = entityManager.merge(pedidoProducto);
        } else {
            entityManager.persist(pedidoProducto);
        }
        return pedidoProducto;
    }

    public void delete(PedidoProductoId id) {
        EntityManager entityManager = DatabaseProxy.getEntityManager();
        EntityTransaction tx = entityManager.getTransaction();
        PedidoProducto pedidoProducto = findById(id);
        if (pedidoProducto != null) {
            entityManager.remove(pedidoProducto);
        }
    }
}
