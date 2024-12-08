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
import javax.xml.bind.annotation.XmlRootElement;
import org.hibernate.annotations.Cascade;

/**
 *
 * @author Edgar
 */
@Entity
@Table(name = "registro_horas")
@XmlRootElement
public class RegistroHoras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "empleado_clave", nullable = false)
        @Cascade(org.hibernate.annotations.CascadeType.DELETE)
    private Empleado empleado_clave;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha", nullable = false)
    private Date fecha;

    @Column(name = "horas_trabajadas", nullable = false)
    private Float horasTrabajadas;

    @Column(name = "horas_extras", nullable = false)
    private Float horasExtras = 0f;

    @Column(name = "es_dia_festivo", nullable = false)
    private Boolean esDiaFestivo = false;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Empleado getEmpleado_clave() {
        return empleado_clave;
    }

    public void setEmpleado_clave(Empleado empleado_clave) {
        this.empleado_clave = empleado_clave;
    }

  
    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Float getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Float horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public Float getHorasExtras() {
        return horasExtras;
    }

    public void setHorasExtras(Float horasExtras) {
        this.horasExtras = horasExtras;
    }

    public Boolean getEsDiaFestivo() {
        return esDiaFestivo;
    }

    public void setEsDiaFestivo(Boolean esDiaFestivo) {
        this.esDiaFestivo = esDiaFestivo;
    }

}
