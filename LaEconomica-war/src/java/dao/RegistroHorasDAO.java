/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.util.ArrayList;
import javax.persistence.TypedQuery;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Calendar;
import java.util.Date;
import model.Empleado;
import model.RegistroHoras;
import proxy.DatabaseProxy;

/**
 *
 * @author Edgar
 */
public class RegistroHorasDAO {

    public List<Object[]> getGastosUltimosMeses(int sucursalId) {
        System.out.println("Iniciando consulta de gastos últimos meses");
        EntityManager em = DatabaseProxy.getEntityManager(); // Obtiene el EntityManager
        List<Object[]> resultados = new ArrayList<>();

        try {
            // Calcular la fecha de inicio (hace 7 meses)
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -7);
            Date fechaInicio = cal.getTime();
            System.out.println("Fecha de inicio: " + fechaInicio);

            // Depuración: verificar qué registros existen en la base
            System.out.println("Registros de horas: " + em.createQuery("SELECT r FROM RegistroHoras r", RegistroHoras.class).getResultList());

            // Consulta JPQL para obtener el mes y las horas trabajadas filtrando por la sucursal ID
            String jpql = "SELECT FUNCTION('MONTH', r.fecha), SUM((r.horasTrabajadas * r.empleado_clave.sueldo) + (r.horasExtras * (r.empleado_clave.sueldo * 2))) "
                    + "FROM RegistroHoras r "
                    + "WHERE r.fecha >= :fechaInicio "
                    + "AND r.empleado_clave.sucursal_id.id = :sucursalId "
                    + "GROUP BY FUNCTION('MONTH', r.fecha) "
                    + "ORDER BY FUNCTION('MONTH', r.fecha) DESC";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("sucursalId", sucursalId);

            System.out.println("Ejecutando consulta: " + query);

            // Ejecutar consulta y obtener resultados
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados);

        } catch (IllegalArgumentException e) {
            System.err.println("Error en los parámetros de la consulta: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error con el estado del EntityManager: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado durante la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); // Asegura cerrar el EntityManager
                System.out.println("EntityManager cerrado.");
            }
        }

        return resultados;
    }

    public List<Object[]> obtenerRegistroPorEmpleado(int claveEmpleado) {
        System.out.println("Iniciando consulta de registros de horas para el empleado: " + claveEmpleado);
        EntityManager em = DatabaseProxy.getEntityManager(); // Obtiene el EntityManager
        List<Object[]> resultados = new ArrayList<>();

        try {
            // Calcular la fecha de inicio (hace 15 días)
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.DAY_OF_YEAR, -15);
            Date fechaInicio = cal.getTime();
            System.out.println("Fecha de inicio para los últimos 15 días: " + fechaInicio);

            // Consulta JPQL para obtener las horas trabajadas filtrando por clave del empleado y fecha
            String jpql = "SELECT r.fecha, r.horasTrabajadas "
                    + "FROM RegistroHoras r "
                    + "WHERE r.fecha >= :fechaInicio "
                    + "AND r.empleado_clave.clave = :claveEmpleado "
                    + "ORDER BY r.fecha DESC";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("claveEmpleado", claveEmpleado);

            System.out.println("Ejecutando consulta: " + query);

            // Ejecutar consulta y obtener resultados
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados);

        } catch (IllegalArgumentException e) {
            System.err.println("Error en los parámetros de la consulta: " + e.getMessage());
            e.printStackTrace();
        } catch (IllegalStateException e) {
            System.err.println("Error con el estado del EntityManager: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error inesperado durante la consulta: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (em != null && em.isOpen()) {
                em.close(); // Asegura cerrar el EntityManager
                System.out.println("EntityManager cerrado.");
            }
        }

        return resultados;
    }
}
