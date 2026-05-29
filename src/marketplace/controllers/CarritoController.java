package marketplace.controllers;

import marketplace.models.*;
import java.util.List;

public class CarritoController {
    private Carrito carrito;

    public CarritoController(Usuario usuario) {
        this.carrito = new Carrito(usuario);
    }

    public boolean agregarProducto(Producto producto, int cantidad) {
        if (producto.getStock() >= cantidad && cantidad > 0) {
            carrito.agregarProducto(producto, cantidad);
            return true;
        }
        return false;
    }

    public void eliminarProducto(Producto producto) {
        carrito.eliminarProducto(producto);
    }

    public double getTotal() {
        return carrito.getTotal();
    }

    public int getCantidadItems() {
        return carrito.getCantidadItems();
    }

    public List<ItemCarrito> getItems() {
        return carrito.getItems();
    }

    public void vaciarCarrito() {
        carrito.vaciar();
    }

    public Carrito getCarrito() {
        return carrito;
    }

    public boolean realizarCompra(ProductoController productoController) {
        // Verificar stock antes de comprar
        for (ItemCarrito item : carrito.getItems()) {
            Producto p = item.getProducto();
            if (p.getStock() < item.getCantidad()) {
                return false;
            }
        }

        // Descontar stock
        for (ItemCarrito item : carrito.getItems()) {
            Producto p = item.getProducto();
            productoController.actualizarStock(p.getId(), p.getStock() - item.getCantidad());
        }

        vaciarCarrito();
        return true;
    }
}