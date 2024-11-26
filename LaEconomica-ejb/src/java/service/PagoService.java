/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.PagoDAO;
import java.util.Date;
import javax.ejb.Stateless;
import model.Empleado;
import model.Pago;

/**
 * Servicio para la gestión de pagos. Proporciona funcionalidades para registrar
 * pagos asociados a empleados.
 * 
 * @author Edgar
 */
@Stateless
public class PagoService {

    // DAO para la interacción con la base de datos
    private final PagoDAO pagoDAO;

    /**
     * Constructor de PagoService.
     * Inicializa la instancia de PagoDAO.
     */
    public PagoService() {
        this.pagoDAO = new PagoDAO();
    }

    /**
     * Registra un nuevo pago para un empleado en la base de datos.
     * 
     * @param empleado    El empleado al que se le asigna el pago.
     * @param totalPagado Monto total a pagar al empleado.
     * @param fechaInicio Fecha de inicio del período del pago.
     * @param fechaFin    Fecha de fin del período del pago.
     * 
     * @throws IllegalArgumentException Si alguno de los parámetros es nulo.
     */
    public void registrarPago(Empleado empleado, double totalPagado, Date fechaInicio, Date fechaFin) {
        // Validación de datos obligatorios
        if (empleado == null || fechaInicio == null || fechaFin == null) {
            throw new IllegalArgumentException("Los datos del pago no pueden ser nulos.");
        }

        // Creación y configuración del objeto Pago
        Pago pago = new Pago();
        pago.setEmpleado(empleado);
        pago.setTotalPagado(totalPagado);
        pago.setFechaInicio(fechaInicio);
        pago.setFechaFin(fechaFin);
        pago.setFechaPago(new Date()); // Asignar la fecha actual como fecha de pago.

        // Registro del pago mediante el DAO
        pagoDAO.registrarPago(pago);
    }
}
