/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.Date;

/**
 *
 * @author Edgar
 */
public class HorasTrabajadasResult {
    private double total;
    private Date fechaMasCercana;
    private Date fechaMasAlejada;

    // Constructor
    public HorasTrabajadasResult(double total, Date fechaMasCercana, Date fechaMasAlejada) {
        this.total = total;
        this.fechaMasCercana = fechaMasCercana;
        this.fechaMasAlejada = fechaMasAlejada;
    }

    // Getters y Setters
    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public Date getFechaMasCercana() {
        return fechaMasCercana;
    }

    public void setFechaMasCercana(Date fechaMasCercana) {
        this.fechaMasCercana = fechaMasCercana;
    }

    public Date getFechaMasAlejada() {
        return fechaMasAlejada;
    }

    public void setFechaMasAlejada(Date fechaMasAlejada) {
        this.fechaMasAlejada = fechaMasAlejada;
    }
}
