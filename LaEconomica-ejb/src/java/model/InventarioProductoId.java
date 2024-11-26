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
public class InventarioProductoId implements Serializable {
    private int inventarioId;
    private int productoId;

    // HashCode y Equals
    @Override
    public int hashCode() {
        return Objects.hash(inventarioId, productoId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        InventarioProductoId that = (InventarioProductoId) obj;
        return inventarioId == that.inventarioId && productoId == that.productoId;
    }

    // Getters y Setters
    public int getInventarioId() {
        return inventarioId;
    }

    public void setInventarioId(int inventarioId) {
        this.inventarioId = inventarioId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
