/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Edgar
 */
@Embeddable
public class VentaProductoId implements Serializable {
    private int ventaId;
    private int productoId;

    // HashCode y Equals
    @Override
    public int hashCode() {
        return Objects.hash(ventaId, productoId);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VentaProductoId that = (VentaProductoId) obj;
        return ventaId == that.ventaId && productoId == that.productoId;
    }

    // Getters y Setters
    public int getVentaId() {
        return ventaId;
    }

    public void setVentaId(int ventaId) {
        this.ventaId = ventaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }
}
