/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "sucursal")
public class Sucursal {
    @Id
    private int id;

    @Column(nullable = false, length = 100)
    private String descripcion;

    @Column(nullable = false, length = 50)
    private String domicilio;

    @Column(nullable = false)
    private int empleados;

    @Column(nullable = false)
    private int inventario;

    @Column(nullable = false)
    private int ventas;

    @ManyToOne
    @JoinColumn(name = "Inventarioid")
    private Inventario inventarioSucursal;

    // Getters y setters
}

