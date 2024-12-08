/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package controller;

import com.sun.xml.ws.client.RequestContext;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.servlet.http.HttpSession;
import model.Empleado;
import model.Producto;
import model.Sucursal;
import model.Venta;
import service.InventarioService;
import service.ProductoService;
import service.VentaService;

/**
 *
 * @author Edgar
 */
@Named(value = "ventaController")
@SessionScoped
public class VentaController implements Serializable {

    private VentaService ventaService = new VentaService();
    private List<Object[]> ventasPorSucursal;
    private List<Object[]> topEmpleados;
    private List<Object[]> ventasPorMes;
    private List<Object[]> ProductosVendidos;
    private Object venta;
    private int sucursalId; // ID de la sucursal seleccionada en la vista.
    private int productoId;
    private int cantidad;
    private HashMap<Producto, Integer> productosConCantidad = new HashMap<>();
    private Empleado empl;
    private Sucursal sucursal;
    private InventarioService productoService = new InventarioService();
    private double totalCompra;
    private double cantidadFinal = 0;
    private double cambio = 0;
    private double dineroRecibido = 0;

    @PostConstruct
    public void init() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(false);
        empl = (Empleado) session.getAttribute("empleadoLogueado");
        sucursal = empl.getSucursal_id();
        this.sucursalId = empl.getSucursal_id().getId(); // ID predeterminado; puedes configurarlo desde la vista.
        cargarVentasIniciales();

    }

    public void generarVenta() {

        if (productosConCantidad.isEmpty()) {
            // Mostrar mensaje de advertencia si no hay productos seleccionados
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN, "No se puede generar la venta", "Debe agregar al menos un producto."));
            return; // Salir del método si no hay productos
        }
        if (dineroRecibido < totalCompra) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es suficiente dinero para pagar los productos", null));
            return;
                    }
        try {
            // Convertir `productosConCantidad` al formato requerido por `crearVenta`
            Map<Producto, float[]> productosConDetalles = new HashMap<>();

            for (Map.Entry<Producto, Integer> entry : productosConCantidad.entrySet()) {
                Producto producto = entry.getKey();
                Integer cantidad = entry.getValue();

                // Obtener el precio unitario del producto desde el servicio
                float precioUnitario = producto.getPrecio();

                // Crear el arreglo `float[]` con [cantidadVendida, precioUnitario]
                productosConDetalles.put(producto, new float[]{cantidad, precioUnitario});
            }

            // Generar la venta usando el servicio
            ventaService.crearVenta(empl, sucursal, productosConDetalles, 0.0f);
cargarVentasIniciales();
            // Mensaje de éxito
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO, "Venta generada exitosamente", null));

            // Limpiar los datos después de la venta
            productosConCantidad.clear();

        } catch (Exception e) {
            // Manejo de errores
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al generar la venta: " + e.getMessage(), null));
        }
    }

    /**
     * Carga inicial de datos al cargar la vista.
     */
    public void cargarVentasIniciales() {
        ventasPorSucursal = ventaService.obtenerVentasPorSucursal(sucursalId);
        topEmpleados = ventaService.obtenerTopEmpleadosPorVentas();
        ventasPorMes = ventaService.obtenerVentasPorMes(sucursalId);
    }

    /**
     * Método para recargar las ventas de una sucursal específica.
     */
    public void recargarVentasPorSucursal() {
        ventasPorSucursal = ventaService.obtenerVentasPorSucursal(sucursalId);
    }

    public String getNombreMes(int mesNumero) {
        String[] meses = {"Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"};
        if (mesNumero >= 1 && mesNumero <= 12) {
            return meses[mesNumero - 1];
        } else {
            return "Mes inválido";
        }
    }

    public void ObtenerProducto(int id, Object ventaSelect) {
        venta = ventaSelect;
        ProductosVendidos = ventaService.obtenerProductosVendidosPorVenta(id);
    }

    public void verificar() {
        Producto p = productoService.verificarProductoEnInventario(sucursalId, productoId);
        if (p != null) {
            boolean encontrado = false;

            for (Map.Entry<Producto, Integer> entry : productosConCantidad.entrySet()) {
                Producto producto = entry.getKey();
                Integer detalles = entry.getValue();

                if (producto.getId() == productoId) {
                    cantidad = detalles + cantidad;

                    if (!productoService.verificarProductoCantidadDisponible(sucursalId, productoId, cantidad)) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                        "Error La Cantidad Solicitada no esta disponible",
                                        "El Producto No Encontrado "));
                        return;
                    } else {
                        productosConCantidad.put(producto, cantidad);
                        encontrado = true;
                        break;
                    }
                }
            }

            if (!encontrado) {
                if (productoService.verificarProductoCantidadDisponible(sucursalId, productoId, cantidad)) {
                    productosConCantidad.put(p, cantidad);
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_INFO,
                                    "El Producto '" + p.getNombre() + "' ha sido registrado exitosamente.",
                                    "El Producto '" + p.getNombre() + "' ha sido registrado exitosamente."));
                } else {
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                    "Error La Cantidad Solicitada no esta disponible",
                                    "El Producto No Encontrado "));
                }
            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "El Producto No Encontrado ",
                            "El Producto No Encontrado "));
        }
    }

    public double total(double a, double b) {
        return a * b;
    }

    public void calcularTotalCompra() {
        totalCompra = productosConCantidad.entrySet().stream()
                .mapToDouble(entry -> total(entry.getKey().getPrecio(), entry.getValue()))
                .sum();
    }

    public void vCantidad(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        int datovp = (int) value;

        if (datovp <= 0) {
            // Add a validation error message
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error La Cantidad Debe ser Mayor a 0", "Producto no valido");
            throw new ValidatorException(message);
        }
    }

    public void procesarPago() {
        System.out.print("entro a cambio");
        if (dineroRecibido > 0) {
            if (dineroRecibido >= totalCompra) {
                cambio = dineroRecibido - totalCompra;
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es suficiente dinero para pagar los productos", null));

            }
        } else {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "No es suficiente dinero para pagar los productos", null));

        }
    }

    /**
     * Getters y setters para que las propiedades sean accesibles en la vista
     * (XHTML).
     */
    public List<Object[]> getVentasPorSucursal() {
        return ventasPorSucursal;
    }

    public List<Object[]> getTopEmpleados() {
        return topEmpleados;
    }

    public List<Object[]> getVentasPorMes() {
        return ventasPorMes;
    }

    public int getSucursalId() {
        return sucursalId;
    }

    public void setSucursalId(int sucursalId) {
        this.sucursalId = sucursalId;
    }

    public List<Object[]> getProductosVendidos() {
        return ProductosVendidos;
    }

    public void setProductosVendidos(List<Object[]> ProductosVendidos) {
        this.ProductosVendidos = ProductosVendidos;
    }

    public Object getVenta() {
        return venta;
    }

    public void setVenta(Object venta) {
        this.venta = venta;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public HashMap<Producto, Integer> getProductosConCantidad() {
        return productosConCantidad;
    }

    public void setProductosConCantidad(HashMap<Producto, Integer> productosConCantidad) {
        this.productosConCantidad = productosConCantidad;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    public double getCantidadFinal() {
        return cantidadFinal;
    }

    public void setCantidadFinal(double cantidadFinal) {
        this.cantidadFinal = cantidadFinal;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public double getDineroRecibido() {
        return dineroRecibido;
    }

    public void setDineroRecibido(double dineroRecibido) {
        this.dineroRecibido = dineroRecibido;
    }

}
