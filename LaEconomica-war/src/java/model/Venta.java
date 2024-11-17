/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "venta")
public class Venta {
    @Id
    private int id;

    @ManyToOne
    @JoinColumn(name = "empleado", nullable = false)
    private Empleado empleado;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fecha;

    @Column
    private Float descuento;

    @ManyToOne
    @JoinColumn(name = "sucursal", nullable = false)
    private Sucursal sucursal;

    // Getters y setters
}

