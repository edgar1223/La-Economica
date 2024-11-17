/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Edgar
 */

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

@Embeddable
public class PedidoProductoId implements Serializable {
    private int pedidoId;
    private int productoId;

    // HashCode y Equals
    @Override
    public int hashCode() {
        return Objects.hash(pedidoId, productoId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        PedidoProductoId that = (PedidoProductoId) obj;
        return pedidoId == that.pedidoId && productoId == that.productoId;
    }

    // Getters y Setters
    public int getPedidoId() {
        return pedidoId;
    }

    public void setPedidoId(int pedidoId) {
        this.pedidoId = pedidoId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
