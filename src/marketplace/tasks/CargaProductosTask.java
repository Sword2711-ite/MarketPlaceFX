package marketplace.tasks;

import javafx.concurrent.Task;


public class CargaProductosTask extends Task<Void> {

    private String modulo;

    public CargaProductosTask(String modulo) {
        this.modulo = modulo;
    }

    @Override
    protected Void call() throws Exception {
        int totalRegistros = 5;

        updateMessage("Conectando con el servidor...");
        Thread.sleep(500); // Simular latencia de conexión

        for (int i = 1; i <= totalRegistros; i++) {

            Thread.sleep(350 + (int)(Math.random() * 200));

            updateProgress(i, totalRegistros);
            updateMessage("Cargando " + modulo + "... " + i + " de " + totalRegistros + " productos");
        }

        updateMessage("Finalizando...");
        Thread.sleep(400);

        return null;
    }
}
