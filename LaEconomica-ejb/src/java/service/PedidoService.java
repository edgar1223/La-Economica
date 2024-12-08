/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.InventarioDao;
import dao.PedidoDAO;
import dao.ProductoDAO;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import model.Pedido;
import model.PedidoProducto;
import model.Producto;

/**
 *
 * @author Edgar
 */
@Stateless

public class PedidoService {

    private PedidoDAO pedidoDAO = new PedidoDAO();
    private InventarioDao InventarioDAO = new InventarioDao();
    private ProductoDAO productoDAO = new ProductoDAO();

    public Pedido agregarPedido(Pedido p) {
        Date fechaActual = new Date();
        p.setFechaSolicitud(fechaActual);
        return pedidoDAO.agregarPedido(p);
    }

    public List<Producto> obtenerProducto(int sucursal) {
        return InventarioDAO.obtenerProductosEnInventarioPorSucursal(sucursal);
    }

    public void agregarProductoAPedido(PedidoProducto pedidoProducto, HashMap<Producto, Integer> productosCantidad, int pedido) {
        System.out.println("agregarProductoAPedido");
            Pedido pe = pedidoDAO.findById(pedido);

        System.out.println("pedido "+pe.getId());

        productosCantidad.forEach((producto, detalles) -> {
            pedidoProducto.setProducto(producto);
            pedidoProducto.setPedido(pe);
            pedidoProducto.setCantidadPedido(detalles);
            pedidoDAO.agregarProductoAPedido(pedidoProducto);

        });

    }
}
