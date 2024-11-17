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

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "pedido")
public class Pedido {
    @Id
    private int id;

    @Column(name = "fecha_solicitud", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date fechaSolicitud;

    @Column(nullable = false, length = 30)
    private String estado;

    // Getters y setters
}
