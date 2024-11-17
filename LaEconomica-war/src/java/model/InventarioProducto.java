package model;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

@Entity
@Table(name = "inventario_producto")
public class InventarioProducto {
    @EmbeddedId
    private InventarioProductoId id;

    @ManyToOne
    @MapsId("inventarioId")
    @JoinColumn(name = "Inventarioid", nullable = false)
    private Inventario inventario;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "Productoid", nullable = false)
    private Producto producto;

    @Column(name = "producto_disponible", nullable = false)
    private int productoDisponible;

    @Column(name = "cantida_minima", nullable = false)
    private int cantidadMinima;

    // Getters y setters
    public InventarioProductoId getId() {
        return id;
    }

    public void setId(InventarioProductoId id) {
        this.id = id;
    }

    public Inventario getInventario() {
        return inventario;
    }

    public void setInventario(Inventario inventario) {
        this.inventario = inventario;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getProductoDisponible() {
        return productoDisponible;
    }

    public void setProductoDisponible(int productoDisponible) {
        this.productoDisponible = productoDisponible;
    }

    public int getCantidadMinima() {
        return cantidadMinima;
    }

    public void setCantidadMinima(int cantidadMinima) {
        this.cantidadMinima = cantidadMinima;
    }
}
