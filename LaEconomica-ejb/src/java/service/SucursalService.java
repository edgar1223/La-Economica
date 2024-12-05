/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.InventarioDao;
import dao.SucursalDAO;
import java.util.List;
import javax.ejb.Stateless;
import model.Empleado;
import model.Inventario;
import model.Sucursal;

/**
 *
 * @author Edgar
 */
@Stateless

public class SucursalService {

    private SucursalDAO sucursalDAO = new SucursalDAO();
    private InventarioDao inventarioDAO = new InventarioDao();

    public void crearSucursal(Sucursal sucursal) {
        try {
            // Validar que la sucursal no sea nula
            if (sucursal == null) {
                System.out.println("Error: La sucursal es nula. No se puede crear.");
                return; // Salir del método para evitar errores posteriores
            }

            // Crear el inventario asociado a la sucursal
            Inventario nuevoInventario = new Inventario();
            nuevoInventario.setNombre(sucursal.getDescripcion()); // Usar el nombre de la sucursal para el inventario

            // Intentar crear el inventario
            inventarioDAO.crear(nuevoInventario);

            // Asignar el inventario creado a la sucursal
            sucursal.setInventarioSucursal(nuevoInventario);

            // Intentar crear la sucursal
            sucursalDAO.crear(sucursal);

            System.out.println("Sucursal creada exitosamente con su inventario asociado.");

        } catch (Exception e) {
            // Manejo de la excepción
            System.err.println("Error al crear la sucursal o su inventario: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void editarSucursal(Sucursal sucursal) {
         sucursalDAO.actualizar(sucursal, sucursal.getId());
    }

    public void eliminarSucursal(int id) {
        sucursalDAO.delete(id);
    }

    public Sucursal obtenerSucursalPorId(int id) {
        return sucursalDAO.obtenerPorId(id);
    }

    public List<Sucursal> listarSucursales() {
        return sucursalDAO.listarTodos();
    }

    public void actualizarSucursal(Sucursal sucursal) {
        sucursalDAO.actualizar(sucursal, sucursal.getId());
    }
}
