/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "empleado")
@XmlRootElement
public class Empleado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "clave")
    private Integer clave;

    @Column(name = "nombre", nullable = false, length = 50)
    private String nombre;

    @Column(name = "apellido_paterno", nullable = false, length = 50)
    private String apellidoPaterno;

    @Column(name = "apellido_materno", length = 50)
    private String apellidoMaterno;

    @Min(value = 18, message = "La edad debe ser al menos 18 años")
    @Max(value = 150, message = "La edad no debe superar los 150 años")
    @Column(name = "edad", nullable = false)
    private Integer edad;

    @Column(name = "domicilio", nullable = false, length = 100)
    private String domicilio;

    @Column(name = "telefono", nullable = false)
    private Integer telefono;

    @Column(name = "jornada", nullable = false, length = 50)
    private String jornada;

    @DecimalMin(value = "207.01", inclusive = false, message = "El sueldo debe ser mayor a 207")
    @Column(name = "sueldo", nullable = false)
    private Float sueldo;

    @Column(name = "jornada_laboral", nullable = false)
    private LocalDate jornadaLaboral;

    @ManyToOne
    @JoinColumn(name = "sucursal_id", nullable = false)
    private Sucursal sucursal_id;

    @Column(name = "password", length = 255)
    private String password;

    public Empleado() {
    }

    public Empleado(Integer clave, String nombre, Sucursal sucursal_id, String password) {
        this.clave = clave;
        this.nombre = nombre;
        this.sucursal_id = sucursal_id;
        this.password = password;
    }
    
    
    
    public Integer getClave() {
        return clave;
    }

    public void setClave(Integer clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoPaterno() {
        return apellidoPaterno;
    }

    public void setApellidoPaterno(String apellidoPaterno) {
        this.apellidoPaterno = apellidoPaterno;
    }

    public String getApellidoMaterno() {
        return apellidoMaterno;
    }

    public void setApellidoMaterno(String apellidoMaterno) {
        this.apellidoMaterno = apellidoMaterno;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public Integer getTelefono() {
        return telefono;
    }

    public void setTelefono(Integer telefono) {
        this.telefono = telefono;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }

    public Float getSueldo() {
        return sueldo;
    }

    public void setSueldo(Float sueldo) {
        this.sueldo = sueldo;
    }

    public LocalDate getJornadaLaboral() {
        return jornadaLaboral;
    }

    public void setJornadaLaboral(LocalDate jornadaLaboral) {
        this.jornadaLaboral = jornadaLaboral;
    }

    public Sucursal getSucursal_id() {
        return sucursal_id;
    }

    public void setSucursal_id(Sucursal sucursal_id) {
        this.sucursal_id = sucursal_id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
