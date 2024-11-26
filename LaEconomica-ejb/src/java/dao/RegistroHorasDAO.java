/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import model.Empleado;
import model.RegistroHoras;
import proxy.DatabaseProxy;

/**
 * Data Access Object (DAO) para la entidad RegistroHoras. Proporciona métodos
 * para realizar consultas relacionadas con registros de horas trabajadas.
 *
 * @author Edgar
 */
public class RegistroHorasDAO implements Serializable {

    /**
     * Obtiene los gastos por horas trabajadas y extras en los últimos 7 meses
     * para una sucursal específica.
     *
     * @param sucursalId ID de la sucursal a consultar.
     * @return Lista de arreglos de objetos donde cada elemento contiene el mes
     * y el gasto total.
     */
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

            // Consulta JPQL para obtener mes y gasto total filtrando por sucursal
            String jpql = "SELECT FUNCTION('MONTH', r.fecha), "
                    + "SUM((r.horasTrabajadas * r.empleado_clave.sueldo) + (r.horasExtras * (r.empleado_clave.sueldo * 2))) "
                    + "FROM RegistroHoras r "
                    + "WHERE r.fecha >= :fechaInicio "
                    + "AND r.empleado_clave.sucursal_id.id = :sucursalId "
                    + "GROUP BY FUNCTION('MONTH', r.fecha) "
                    + "ORDER BY FUNCTION('MONTH', r.fecha) DESC";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("sucursalId", sucursalId);

            // Ejecutar la consulta y obtener resultados
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados);

        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar gastos de los últimos meses");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Obtiene los registros de horas trabajadas de los últimos 15 días para un
     * empleado específico.
     *
     * @param claveEmpleado Clave del empleado a consultar.
     * @return Lista de arreglos de objetos donde cada elemento contiene la
     * fecha y las horas trabajadas.
     */
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

            // Consulta JPQL para obtener fecha y horas trabajadas filtrando por clave del empleado
            String jpql = "SELECT r.fecha, r.horasTrabajadas "
                    + "FROM RegistroHoras r "
                    + "WHERE r.fecha >= :fechaInicio "
                    + "AND r.empleado_clave.clave = :claveEmpleado "
                    + "ORDER BY r.fecha DESC";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("fechaInicio", fechaInicio);
            query.setParameter("claveEmpleado", claveEmpleado);

            // Ejecutar la consulta y obtener resultados
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados: " + resultados);

        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar registros por empleado");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Obtiene los registros de horas trabajadas y detalles del empleado para un
     * mes y sucursal específicos.
     *
     * @param mes Mes a consultar.
     * @param sucursalId ID de la sucursal a consultar.
     * @return Lista de arreglos de objetos donde cada elemento contiene los
     * detalles del empleado y registros.
     */
    public List<Object[]> getEmpleadoYRegistrosPorMesYSucursal(int mes, int sucursalId) {
        System.out.println("Iniciando consulta para obtener empleado y registros por mes y sucursal");
        EntityManager em = DatabaseProxy.getEntityManager(); // Obtiene el EntityManager
        List<Object[]> resultados = new ArrayList<>();

        try {
            // Consulta JPQL para obtener detalles del empleado y registros de RegistroHoras
            String jpql = "SELECT e.nombre, e.sueldo, r.fecha, r.horasTrabajadas, r.horasExtras, r.esDiaFestivo "
                    + "FROM RegistroHoras r "
                    + "JOIN r.empleado_clave e "
                    + "WHERE FUNCTION('MONTH', r.fecha) = :mes "
                    + "AND e.sucursal_id.id = :sucursalId "
                    + "ORDER BY r.fecha ASC";

            TypedQuery<Object[]> query = em.createQuery(jpql, Object[].class);
            query.setParameter("mes", mes);
            query.setParameter("sucursalId", sucursalId);

            // Ejecutar la consulta y obtener resultados
            resultados = query.getResultList();
            System.out.println("Consulta exitosa. Resultados obtenidos: " + resultados.size());

        } catch (Exception e) {
            manejarExcepcion(e, "Error al consultar empleado y registros por mes y sucursal");
        } finally {
            cerrarEntityManager(em);
        }

        return resultados;
    }

    /**
     * Maneja excepciones durante las consultas.
     *
     * @param e Excepción capturada.
     * @param mensaje Mensaje adicional para el error.
     */
    private void manejarExcepcion(Exception e, String mensaje) {
        System.err.println(mensaje + ": " + e.getMessage());
        e.printStackTrace();
    }

    public void agregarHoras(RegistroHoras horas) {
        EntityManager em = DatabaseProxy.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(horas);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e; // Propaga el error para que sea manejado en la capa de servicio
        } finally {
            em.close();
        }
    }

    /**
     * Cierra el EntityManager de manera segura.
     *
     * @param em Instancia de EntityManager a cerrar.
     */
    private void cerrarEntityManager(EntityManager em) {
        if (em != null && em.isOpen()) {
            em.close();
            System.out.println("EntityManager cerrado.");
        }
    }
}
