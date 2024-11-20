/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "venta_producto")
@XmlRootElement
public class VentaProducto {
    @EmbeddedId
    private VentaProductoId id;

    @ManyToOne
    @MapsId("ventaId")
    @JoinColumn(name = "Ventaid", nullable = false)
    private Venta venta;

    @ManyToOne
    @MapsId("productoId")
    @JoinColumn(name = "Productoid", nullable = false)
    private Producto producto;

    @Column(name = "cantidad_vendida", nullable = false)
    private int cantidadVendida;

    @Column(name = "precio_unitario", nullable = false)
    private float precioUnitario;

    // Getters y setters
}

