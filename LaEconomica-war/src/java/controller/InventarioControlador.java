package controller;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import model.Empleado;
import model.InventarioProducto;
import model.Producto;
import service.InventarioService;

/**
 * Controlador para la gestión del inventario de productos. Maneja las
 * operaciones relacionadas con el inventario, como cargar datos, actualizar
 * información y registrar nuevos productos.
 *
 * @author Edgar
 */
@Named(value = "inventarioControlador")
@SessionScoped
public class InventarioControlador implements Serializable {

    /**
     * Servicio de inventario que proporciona acceso a los datos de inventario.
     */
    private InventarioService iventarioService = new InventarioService();

    // Listas para almacenar información relacionada con el inventario
    private List<InventarioProducto> listInventarioProducto;
    private List<Object[]> productoVendidos;
    private List<Object[]> producroMenosVendidos;
    private List<Producto> productosDisponibles;
    private List<Producto> productosNoEnInventario;
    
    // Variables para manejar productos seleccionados o nuevos
    private InventarioProducto inventarioProducto = new InventarioProducto();
    private InventarioProducto inventarioProductoSeleccionado = new InventarioProducto();

    // IDs necesarios para identificar la sucursal y los productos
    private int sucursalId;
    private int productoId;

    /**
     * Constructor vacío. Inicializa una nueva instancia del controlador.
     */
    public InventarioControlador() {
    }

    /**
     * Carga el inventario de productos asociados a la sucursal del empleado
     * logueado. También obtiene estadísticas de productos más y menos vendidos.
     */
    private void cargarIventario() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");

        // Cargar inventario y estadísticas
        listInventarioProducto = iventarioService.obtenerProductosPorSucursal(empl.getSucursal_id().getId());
        productoVendidos = iventarioService.obtenerProductosMasVendidos(empl.getSucursal_id().getId());
        producroMenosVendidos = iventarioService.obtenerProductosMenosVendidos(empl.getSucursal_id().getId());
    }

    /**
     * Guarda los cambios realizados a un producto en el inventario. Muestra
     * mensajes de éxito o error según el resultado.
     */
    public void guardarCambios() {
        try {
            iventarioService.actualizarIventarioProducto(inventarioProductoSeleccionado);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Inventario actualizado correctamente",
                            "Producto: " + inventarioProductoSeleccionado.getProducto().getNombre()));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al actualizar el inventario", e.getMessage()));
        }
    }

    /**
     * Obtiene una lista de productos disponibles que no están en el inventario.
     * La lista se carga solo si no está inicializada o está vacía.
     *
     * @return Lista de productos disponibles.
     */
    public List<Producto> obtenerProductosDisponibles() {
        if (productosDisponibles == null || productosDisponibles.isEmpty()) {
            FacesContext facesContext = FacesContext.getCurrentInstance();
            HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
            Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");
            productosDisponibles = iventarioService.obtenerProductosNoEnInventarioPorSucursal(empl.getSucursal_id().getId());
        }
        return productosDisponibles;
    }

    /**
     * Registra un nuevo producto en el inventario de la sucursal actual.
     * Muestra un mensaje según el éxito o fallo de la operación.
     */
    public void registrarProducto() {
        try {
            Producto producto = new Producto();
            producto.setId(productoId); // Asigna el ID del producto seleccionado
            inventarioProducto.setProducto(producto);

            // Agregar producto al inventario
            iventarioService.agregarProductoAlInventario(inventarioProducto, sucursalId);
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage("Producto registrado exitosamente"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "Error al registrar el producto", e.getMessage()));
        }
    }

    /**
     * Determina la clase CSS a aplicar según la cantidad de producto disponible
     * comparada con el nivel mínimo requerido.
     *
     * @param disponible Cantidad disponible.
     * @param minima Nivel mínimo requerido.
     * @return Clase CSS (vacía o "warning-style").
     */
    public String obtenerClaseCantidad(int disponible, int minima, String nombre) {
        if (disponible <= minima) {
            if (disponible == 0) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Advertencia: El stock del producto'" + nombre + "' es 0", null));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_WARN, "Advertencia: El stock del producto'" + nombre + "' es bajo", null));
            }
        }
        return disponible <= minima ? "warning-style" : "";
    }

   
    /**
     * Limpia las variables de producto cargado y seleccionado.
     */
    public void EliminarProductoCargado() {
        inventarioProducto = new InventarioProducto();
        inventarioProductoSeleccionado = new InventarioProducto();
    }

    // Getters y setters para las propiedades de la clase
    public List<InventarioProducto> getListInventarioProducto() {
        if (listInventarioProducto == null) {
            cargarIventario();
        }
        return listInventarioProducto;
    }

    public void setListInventarioProducto(List<InventarioProducto> listInventarioProducto) {
        this.listInventarioProducto = listInventarioProducto;
    }

    public List<Object[]> getProducroMenosVendidos() {
        if (producroMenosVendidos == null) {
            cargarIventario();
        }
        return producroMenosVendidos;
    }

    public void setProducroMenosVendidos(List<Object[]> producroMenosVendidos) {
        this.producroMenosVendidos = producroMenosVendidos;
    }

    public List<Producto> getProductosDisponibles() {
        return productosDisponibles;
    }

    public void setProductosDisponibles(List<Producto> productosDisponibles) {
        this.productosDisponibles = productosDisponibles;
    }

    public List<Producto> getProductosNoEnInventario() {
        if (productosNoEnInventario == null) {
            productosNoEnInventario = obtenerProductosDisponibles();
        }
        return productosNoEnInventario;
    }

    public void setProductosNoEnInventario(List<Producto> productosNoEnInventario) {
        this.productosNoEnInventario = productosNoEnInventario;
    }

    public InventarioProducto getInventarioProducto() {
        return inventarioProducto;
    }

    public void setInventarioProducto(InventarioProducto inventarioProducto) {
        this.inventarioProducto = inventarioProducto;
    }

    public InventarioProducto getInventarioProductoSeleccionado() {
        return inventarioProductoSeleccionado;
    }

    public void setInventarioProductoSeleccionado(InventarioProducto inventarioProductoSeleccionado) {
        this.inventarioProductoSeleccionado = inventarioProductoSeleccionado;
    }

    public int getSucursalId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        Empleado empl = (Empleado) session.getAttribute("empleadoLogueado");
        sucursalId = empl.getSucursal_id().getId();

        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public List<Object[]> getProductoVendidos() {
        if (productoVendidos == null) {
            cargarIventario();
        }
        return productoVendidos;
    }

    public void setProductoVendidos(List<Object[]> productoVendidos) {
        this.productoVendidos = productoVendidos;
    }

}
