/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

/**
 *
 * @author Edgar
 */
public class ProductAlert {
    private int inventarioId;
    private String productoNombre;
    private int cantidadDisponible;

    public ProductAlert(int inventarioId, String productoNombre, int cantidadDisponible) {
        this.inventarioId = inventarioId;
        this.productoNombre = productoNombre;
        this.cantidadDisponible = cantidadDisponible;
    }

    @Override
    public String toString() {
        return "Inventario ID: " + inventarioId + ", Producto: " + productoNombre + 
               ", Disponible: " + cantidadDisponible;
    }
}

