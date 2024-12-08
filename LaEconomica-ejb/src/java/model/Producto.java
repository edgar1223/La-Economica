/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.DecimalMin;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "producto")
@XmlRootElement
public class Producto {
    @Id
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;
    
    @DecimalMin(value = "0.1", inclusive = false, message = "El precio debe ser mayor a 0.0")
    @Column(nullable = false)
    private float precio;

    @Column(length = 50)
    private String marca;

    @Column(name = "fecha_caducidad")
    @Temporal(TemporalType.DATE)
    private Date fechaCaducidad;

    @Column(length = 200)
    private String descripcion;

    @Column(nullable = false, length = 100)
    private String proveedor;

    @Column(nullable = false)
    private float promocion;
    
    @Column(nullable = true)
    private float precio_original;

    // Getters y setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public Date getFechaCaducidad() {
        return fechaCaducidad;
    }

    public void setFechaCaducidad(Date fechaCaducidad) {
        this.fechaCaducidad = fechaCaducidad;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getProveedor() {
        return proveedor;
    }

    public void setProveedor(String proveedor) {
        this.proveedor = proveedor;
    }

    public float getPromocion() {
        return promocion;
    }

    public void setPromocion(float promocion) {
        this.promocion = promocion;
    }

    public float getPrecio_original() {
        return precio_original;
    }

    public void setPrecio_original(float precio_original) {
        this.precio_original = precio_original;
    }
    
    
}
