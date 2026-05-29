package marketplace.tasks;

import marketplace.controllers.ProductoController;
import marketplace.models.Producto;
import javafx.concurrent.Task;

public class CargaProductosTask extends Task<Void> {
    private ProductoController productoController;

    public CargaProductosTask(ProductoController productoController) {
        this.productoController = productoController;
    }

    @Override
    protected Void call() throws Exception {
        int totalRegistros = productoController.getAllProductos().size();

        updateMessage("Conectando con el servidor...");
        Thread.sleep(500);

        int i = 1;
        for (Producto p : productoController.getAllProductos()) {
            Thread.sleep(200);
            updateProgress(i, totalRegistros);
            updateMessage("Cargando producto: " + p.getNombre() + " (" + i + "/" + totalRegistros + ")");
            i++;
        }

        updateMessage("Finalizando...");
        Thread.sleep(400);

        return null;
    }
}