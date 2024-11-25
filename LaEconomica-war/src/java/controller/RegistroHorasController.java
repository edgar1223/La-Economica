/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import service.RegistroHorasService;
import service.PagoService;

import java.io.Serializable;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;

import model.Empleado;
import model.HorasTrabajadasResult;
import model.RegistroHoras;

/**
 * Controlador para gestionar el registro de horas trabajadas y pagos de
 * empleados. Proporciona funciones para obtener registros, calcular horas y
 * realizar pagos.
 *
 * @author Edgar
 */
@Named(value = "registroHorasController")
@SessionScoped
public class RegistroHorasController implements Serializable {

    // Servicios
    private RegistroHorasService registroHorasService;
    private PagoService pagoService;

    // Datos y variables de estado
    private List<Object[]> gastosMensuales;
    private List<Object[]> gastosMensualesPorMes;
    private Map<Integer, HorasTrabajadasResult> totalSueldo = new HashMap<>();
    private String mes;
    private RegistroHoras registroHoras=new RegistroHoras();

    // ========================
    // Métodos para gastos mensuales
    // ========================
    /**
     * Obtiene los gastos mensuales de los últimos meses.
     *
     * @return Lista de objetos con los gastos mensuales.
     */
    public List<Object[]> getGastosMensuales() {
        if (gastosMensuales == null) {
            try {
                registroHorasService = new RegistroHorasService();
                gastosMensuales = registroHorasService.obtenerGastosUltimosMeses(1);
            } catch (Exception e) {
                System.err.println("Error al cargar gastos mensuales: " + e.getMessage());
            }
        }
        return gastosMensuales;
    }

    /**
     * Establece la lista de gastos mensuales.
     *
     * @param gastosMensuales Lista de gastos mensuales.
     */
    public void setGastosMensuales(List<Object[]> gastosMensuales) {
        this.gastosMensuales = gastosMensuales;
    }

    /**
     * Obtiene los gastos mensuales por un mes específico y sucursal.
     *
     * @param mes Mes del que se desean obtener los registros.
     */
    public void getEmpleadoYRegistrosPorMesYSucursal(int mes) {
        try {
            registroHorasService = new RegistroHorasService();
            gastosMensualesPorMes = registroHorasService.getEmpleadoYRegistrosPorMesYSucursal(mes, 1);
            System.out.print(gastosMensualesPorMes.get(2)[2]);
            obtenerMes(gastosMensualesPorMes.get(2)[2]);
        } catch (Exception e) {
            System.err.println("Error al cargar registros por mes: " + e.getMessage());
        }
    }

    /**
     * Establece la lista de gastos mensuales por mes.
     *
     * @param gastosMensualesPorMes Lista de gastos mensuales por mes.
     */
    public void setGastosMensualesPorMes(List<Object[]> gastosMensualesPorMes) {
        this.gastosMensualesPorMes = gastosMensualesPorMes;
    }

    /**
     * Obtiene la lista de gastos mensuales por mes.
     *
     * @return Lista de objetos con los gastos mensuales por mes.
     */
    public List<Object[]> getGastosMensualesPorMes() {
        return gastosMensualesPorMes;
    }

    // ========================
    // Métodos de empleados y sueldos
    // ========================
    /**
     * Calcula las horas trabajadas de un empleado y actualiza el mapa de
     * sueldos.
     *
     * @param clave Clave del empleado.
     * @param sueldo Sueldo del empleado.
     */
    public void obtenerHorasTrabajadasEmpleado(int clave, float sueldo) {
        System.out.println("Calculando horas trabajadas para clave: " + clave + ", sueldo: " + sueldo);
        try {
            registroHorasService = new RegistroHorasService();
            HorasTrabajadasResult horasResult = registroHorasService.obtenerHorasTrabajadasEmpleado(clave, sueldo);
            totalSueldo.put(clave, horasResult);
        } catch (Exception e) {
            System.err.println("Error al calcular horas trabajadas: " + e.getMessage());
        }
    }

    /**
     * Realiza el pago de un empleado.
     *
     * @param empleado Objeto del empleado.
     */
    public void pagar(Empleado empleado) {
        System.out.println("Pagando " + empleado);
        try {
            HorasTrabajadasResult horasResult = totalSueldo.get(empleado.getClave());
            if (horasResult == null) {
                System.err.println("No se encontraron datos de sueldo para el empleado con clave: " + empleado.getClave());
                return;
            }
            pagoService = new PagoService();
            pagoService.registrarPago(empleado, horasResult.getTotal(), horasResult.getFechaMasAlejada(), horasResult.getFechaMasCercana());
            System.out.println("Pago registrado correctamente para clave: " + empleado.getClave());

            FacesMessage messagePago = new FacesMessage(FacesMessage.SEVERITY_INFO, "Pago registrado correctamente para el empleado con clave: " + empleado.getClave(),
                    "Pago registrado correctamente para el empleado con clave: " + empleado.getClave());
            FacesContext.getCurrentInstance().addMessage(null, messagePago);
        } catch (Exception e) {
            System.err.println("Error al realizar el pago: " + e.getMessage());
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error",
                    "No se pudo registrar el pago: " + e.getMessage());
            FacesContext.getCurrentInstance().addMessage(null, message);
        }
    }

    public void guardarRegistroHoras(Empleado empleado,RegistroHoras registroHoras) {
        registroHorasService = new RegistroHorasService();
        registroHoras.setEmpleado_clave(empleado);
        registroHoras.setFecha(new Date()); // Calcula la fecha actual
        boolean aux = registroHorasService.agregarHoras(registroHoras);
        if (aux) {
            FacesMessage messageHora = new FacesMessage(FacesMessage.SEVERITY_INFO, "Horas registrado de empleo: " + empleado.getClave(),
                    "Pago registrado correctamente para el empleado con clave: " + empleado.getClave());
            FacesContext.getCurrentInstance().addMessage(null, messageHora);
        } else {
            FacesMessage messageHora = new FacesMessage(FacesMessage.SEVERITY_ERROR, "No se pudo registrar la hora",
                    "No se pudo registrar el pago: ");
            FacesContext.getCurrentInstance().addMessage(null, messageHora);
        }
        // Reinicia el objeto para nuevos registros
        registroHoras = null;
    }

    /**
     * Obtiene el resultado de horas trabajadas por clave.
     *
     * @param clave Clave del empleado.
     * @return Resultado de horas trabajadas o null si no se encuentra.
     */
    public HorasTrabajadasResult getHorasTrabajadasPorClave(int clave) {
        return totalSueldo.getOrDefault(clave, null);
    }

    /**
     * Establece el mapa de sueldos totales.
     *
     * @param totalSueldo Mapa de sueldos.
     */
    public void setTotalSueldo(Map<Integer, HorasTrabajadasResult> totalSueldo) {
        this.totalSueldo = totalSueldo;
    }

    /**
     * Obtiene el mapa de sueldos totales.
     *
     * @return Mapa de sueldos.
     */
    public Map<Integer, HorasTrabajadasResult> getTotalSueldo() {
        return totalSueldo;
    }

    // ========================
    // Utilidades
    // ========================
    /**
     * Convierte un número de mes a su nombre correspondiente.
     *
     * @param mes Número del mes (1-12).
     * @return Nombre del mes o "Mes desconocido" si es inválido.
     */
    public String getNombreMes(int mes) {
        String[] meses = new DateFormatSymbols().getMonths();
        if (mes >= 1 && mes <= 12) {
            return meses[mes - 1]; // Ajustar índice (1=enero, 12=diciembre)
        }
        return "Mes desconocido";
    }

    /**
     * Obtiene el nombre del mes de una fecha.
     *
     * @param fecha Objeto de tipo Date.
     */
    public void obtenerMes(Object fecha) {
        System.out.println("Obteniendo nombre del mes...");
        if (fecha instanceof Date) {
            SimpleDateFormat monthFormat = new SimpleDateFormat("MMMM");
            mes = monthFormat.format((Date) fecha);
        }
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public RegistroHoras getRegistroHoras() {
        return registroHoras;
    }

    public void setRegistroHoras(RegistroHoras registroHoras) {
        this.registroHoras = registroHoras;
    }

}
