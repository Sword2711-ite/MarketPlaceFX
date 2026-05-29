package marketplace.models;

import java.util.ArrayList;
import java.util.List;

public class Carrito {
    private List<ItemCarrito> items;
    private Usuario usuario;

    public Carrito(Usuario usuario) {
        this.items = new ArrayList<>();
        this.usuario = usuario;
    }

    public void agregarProducto(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId() == producto.getId()) {
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        items.add(new ItemCarrito(producto, cantidad));
    }

    public void eliminarProducto(Producto producto) {
        items.removeIf(item -> item.getProducto().getId() == producto.getId());
    }

    public double getTotal() {
        double total = 0;
        for (ItemCarrito item : items) {
            total += item.getSubtotal();
        }
        return total;
    }

    public int getCantidadItems() {
        int total = 0;
        for (ItemCarrito item : items) {
            total += item.getCantidad();
        }
        return total;
    }

    public List<ItemCarrito> getItems() { return items; }
    public void vaciar() { items.clear(); }
    public Usuario getUsuario() { return usuario; }
}