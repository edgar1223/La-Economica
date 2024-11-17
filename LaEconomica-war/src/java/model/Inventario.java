/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "inventario")
public class Inventario {
    @Id
    private int id;

    @Column(nullable = false, length = 100)
    private String nombre;

    // Getters y setters
}
