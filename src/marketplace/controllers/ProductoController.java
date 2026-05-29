package marketplace.controllers;

import marketplace.models.Producto;
import marketplace.utils.DataStore;
import java.util.List;

public class ProductoController {
    private DataStore dataStore;

    public ProductoController() {
        this.dataStore = DataStore.getInstance();
    }

    public List<Producto> getAllProductos() {
        return dataStore.getAllProductos();
    }

    public Producto getProducto(int id) {
        return dataStore.getProducto(id);
    }

    public void agregarProducto(String nombre, double precio, int stock, String categoria) {
        Producto nuevo = new Producto(0, nombre, precio, stock, categoria);
        dataStore.agregarProducto(nuevo);
    }

    public void actualizarProducto(int id, String nombre, double precio, int stock, String categoria) {
        Producto producto = dataStore.getProducto(id);
        if (producto != null) {
            producto.setNombre(nombre);
            producto.setPrecio(precio);
            producto.setStock(stock);
            producto.setCategoria(categoria);
            dataStore.actualizarProducto(producto);
        }
    }

    public void eliminarProducto(int id) {
        dataStore.eliminarProducto(id);
    }

    public void actualizarStock(int id, int nuevoStock) {
        Producto producto = dataStore.getProducto(id);
        if (producto != null) {
            producto.setStock(nuevoStock);
            dataStore.actualizarProducto(producto);
        }
    }
}