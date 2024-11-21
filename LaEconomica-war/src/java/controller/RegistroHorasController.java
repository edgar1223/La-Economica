/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import service.RegistroHorasService;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.text.DateFormatSymbols;
import org.primefaces.model.charts.line.LineChartDataSet;
import org.primefaces.model.charts.line.LineChartModel;
import org.primefaces.model.charts.optionconfig.title.Title;
import org.primefaces.model.charts.optionconfig.legend.Legend;
import org.primefaces.model.charts.ChartData;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.inject.Inject;
import model.RegistroHoras;

/**
 *
 * @author Edgar
 */
@Named(value = "registroHorasController")
@RequestScoped
public class RegistroHorasController implements Serializable {

    @Inject
    private RegistroHorasService registroHorasService;

    private List<Object[]> gastosMensuales;
    //mejor manjear un map con calve empleado y totalSueldo
    private double totalSueldo;



    public List<Object[]> getGastosMensuales() {
        if(gastosMensuales==null){
        try {
            gastosMensuales = registroHorasService.obtenerGastosUltimosMeses(1);
        } catch (Exception e) {
            System.err.println("Error al cargar gastos mensuales: " + e.getMessage());
        }
        }
        return gastosMensuales;
    }

    public void setGastosMensuales(List<Object[]> gastosMensuales) {
        this.gastosMensuales = gastosMensuales;
    }

    public String getNombreMes(int mes) {
        String[] meses = new DateFormatSymbols().getMonths();
        if (mes >= 1 && mes <= 12) {
            return meses[mes - 1]; // Ajustar índice (1=enero, 12=diciembre)
        }
        return "Mes desconocido";
    }

    public void obtenerHorasTrabajadasEmpleado(int clave, float sueldo) {
        System.out.println("Calculando horas trabajadas para clave: " + clave + ", sueldo: " + sueldo);
        try {
            totalSueldo = registroHorasService.ObtenerHorasTrabajasEmpleado(clave, sueldo);
            System.out.println("Total sueldo calculado: " + totalSueldo);
        } catch (Exception e) {
            System.err.println("Error al calcular horas trabajadas: " + e.getMessage());
        }
    }

    public double getTotalSueldo() {
        return totalSueldo;
    }

    public void setTotalSueldo(double totalSueldo) {
        this.totalSueldo = totalSueldo;
    }
}
