/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.RegistroHorasDAO;
import java.util.Date;
import javax.enterprise.context.ApplicationScoped;
import java.util.List;
import javax.inject.Inject;
import model.RegistroHoras;

/**
 *
 * @author Edgar
 */
@ApplicationScoped
public class RegistroHorasService {

    @Inject
    private RegistroHorasDAO registroHorasDAO;

    public List<Object[]> obtenerGastosUltimosMeses(int sucursalId) {
        System.out.println("Obteniendo gastos últimos meses desde el DAO");
        return registroHorasDAO.getGastosUltimosMeses(sucursalId);
    }

    public double ObtenerHorasTrabajasEmpleado(int claveEmpleado, Float sueldo) {
        System.out.println("Obteniendo horas trabajadas para el empleado con clave: " + claveEmpleado);

        // Obtener los registros de horas desde el DAO
        List<Object[]> registros = registroHorasDAO.obtenerRegistroPorEmpleado(claveEmpleado);
        System.out.println(registros);

        // Verificar si la lista está vacía o nula
        if (registros == null || registros.isEmpty()) {
            System.out.println("No se encontraron registros para el empleado con clave: " + claveEmpleado);
            return 0; // Devuelve 0 si no hay registros
        }

        // Variable para acumular el total
        double total = 0;

        // Recorrer los registros y calcular el sueldo para cada uno
        for (Object[] registro : registros) {
            try {
                // Extraer los valores de fecha y horasTrabajadas del Object[]
                Date fecha = (Date) registro[0]; // Fecha (Object[0])

                // Manejar el caso en el que registro[1] sea Float
                double horasTrabajadas = ((Number) registro[1]).doubleValue(); // Convertir Float a Double

                // Verificar si las horas trabajadas son válidas
                if (horasTrabajadas < 0) {
                    System.out.println("Error: Las horas trabajadas no pueden ser negativas.");
                    continue; // Saltar al siguiente registro si hay un valor incorrecto
                }

                // Calcular el sueldo total para este registro
                double sueldoPorHoras = (horasTrabajadas * sueldo);

                // Sumar al total
                total += sueldoPorHoras;

            } catch (Exception e) {
                // Capturar cualquier error en el cálculo o si algún registro tiene datos inválidos
                System.err.println("Error procesando el registro: " + e.getMessage());
                continue; // Saltar este registro y seguir con el siguiente
            }
        }

        // Devolver el total calculado
        return total;
    }
}
