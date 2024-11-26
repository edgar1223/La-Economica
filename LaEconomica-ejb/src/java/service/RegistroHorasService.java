/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.RegistroHorasDAO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import model.HorasTrabajadasResult;
import model.RegistroHoras;

/**
 * Servicio para la gestión de registros de horas trabajadas por empleados.
 * Proporciona métodos para obtener información relacionada con horas trabajadas,
 * cálculos de sueldo y reportes por sucursal o empleado.
 * 
 * @author Edgar
 */
@Stateless
public class RegistroHorasService {

    // Instancia del DAO para acceso a datos
    private RegistroHorasDAO registroHorasDAO = new RegistroHorasDAO();
    public boolean agregarHoras(RegistroHoras horas){
     try {
            registroHorasDAO.agregarHoras(horas);
            return true; // Operación exitosa
        } catch (Exception e) {
            // Aquí puedes registrar el error si es necesario
            System.err.println("Error al agregar las horas: " + e.getMessage());
            return false; // Operación fallida
        }
    } 
    /**
     * Obtiene los gastos de los últimos meses para una sucursal específica.
     * 
     * @param sucursalId ID de la sucursal.
     * @return Lista de objetos con los datos de gastos.
     */
    public List<Object[]> obtenerGastosUltimosMeses(int sucursalId) {
        System.out.println("Obteniendo gastos últimos meses desde el DAO");
        return registroHorasDAO.getGastosUltimosMeses(sucursalId);
    }

    /**
     * Calcula las horas trabajadas y el sueldo total para un empleado
     * basándose en los registros obtenidos.
     * 
     * @param claveEmpleado Clave del empleado.
     * @param sueldo Tarifa por hora del empleado.
     * @return Objeto HorasTrabajadasResult con el total y las fechas relevantes.
     */
    public HorasTrabajadasResult obtenerHorasTrabajadasEmpleado(int claveEmpleado, Float sueldo) {
        System.out.println("Obteniendo horas trabajadas para el empleado con clave: " + claveEmpleado);

        // Obtener registros desde el DAO
        List<Object[]> registros = registroHorasDAO.obtenerRegistroPorEmpleado(claveEmpleado);

        if (registros == null || registros.isEmpty()) {
            System.out.println("No se encontraron registros para el empleado con clave: " + claveEmpleado);
            return new HorasTrabajadasResult(0, null, null);
        }

        double total = 0;
        Date fechaMasCercana = null;
        Date fechaMasAlejada = null;

        for (Object[] registro : registros) {
            try {
                Date fecha = (Date) registro[0];
                double horasTrabajadas = ((Number) registro[1]).doubleValue();

                if (horasTrabajadas < 0) {
                    System.out.println("Error: Las horas trabajadas no pueden ser negativas.");
                    continue;
                }

                if (fechaMasCercana == null || fecha.before(fechaMasCercana)) {
                    fechaMasCercana = fecha;
                }
                if (fechaMasAlejada == null || fecha.after(fechaMasAlejada)) {
                    fechaMasAlejada = fecha;
                }

                total += horasTrabajadas * sueldo;
            } catch (Exception e) {
                System.err.println("Error procesando el registro: " + e.getMessage());
                continue;
            }
        }

        return new HorasTrabajadasResult(total, fechaMasCercana, fechaMasAlejada);
    }

    /**
     * Obtiene los registros de empleados y horas trabajadas por mes y sucursal,
     * calculando el pago total por empleado incluyendo horas extras.
     * 
     * @param mes Mes a consultar.
     * @param sucursalId ID de la sucursal.
     * @return Lista de registros con los cálculos realizados.
     */
    public List<Object[]> getEmpleadoYRegistrosPorMesYSucursal(int mes, int sucursalId) {
        List<Object[]> registrosOriginales = registroHorasDAO.getEmpleadoYRegistrosPorMesYSucursal(mes, sucursalId);
        List<Object[]> registrosConCalculos = new ArrayList<>();

        for (Object[] registro : registrosOriginales) {
            String nombreEmpleado = (String) registro[0];
            Float sueldo = (Float) registro[1];
            Date fecha = (Date) registro[2];
            Float horasTrabajadas = (Float) registro[3];
            Float horasExtras = (Float) registro[4];
            Boolean esDiaFestivo = (Boolean) registro[5];

            Float totalPagoHoras = (sueldo * horasTrabajadas) + ((sueldo * 2) * horasExtras);

            Object[] registroConCalculo = {
                nombreEmpleado,
                sueldo,
                fecha,
                horasTrabajadas,
                horasExtras,
                esDiaFestivo,
                totalPagoHoras
            };

            registrosConCalculos.add(registroConCalculo);
        }

        return registrosConCalculos;
    }
}
