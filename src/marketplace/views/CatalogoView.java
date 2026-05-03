package marketplace.views;

import marketplace.controllers.*;
import marketplace.models.*;
import marketplace.tasks.CargaProductosTask;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CatalogoView {
    private ProductoController productoController = new ProductoController();
    private CarritoController carritoController;
    private Stage primaryStage;
    private Label cartCountLabel;

    /**
     * Punto de entrada: muestra pantalla de carga en segundo plano
     * antes de renderizar el catálogo de productos.
     */
    public void start(Stage stage, Usuario usuario) {
        this.primaryStage = stage;
        this.carritoController = new CarritoController(usuario);
        mostrarPantallaCarga(usuario);
    }

    /**
     * Muestra una pantalla de carga con barra de progreso mientras
     * un hilo secundario (Task) simula la carga de datos.
     * Esto evita que la interfaz se congele durante la operación.
     */
    private void mostrarPantallaCarga(Usuario usuario) {
        VBox loadingBox = new VBox(25);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2);");

        Label titleLabel = new Label("MarketPlace FX");
        titleLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox cardBox = new VBox(15);
        cardBox.setAlignment(Pos.CENTER);
        cardBox.setMaxWidth(450);
        cardBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 40;");

        Label loadingLabel = new Label("Preparando tu catálogo...");
        loadingLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: #333;");

        ProgressBar progressBar = new ProgressBar(0);
        progressBar.setPrefWidth(350);
        progressBar.setStyle("-fx-accent: #667eea;");

        Label statusLabel = new Label("Iniciando...");
        statusLabel.setStyle("-fx-font-size: 13px; -fx-text-fill: #666;");

        cardBox.getChildren().addAll(loadingLabel, progressBar, statusLabel);
        loadingBox.getChildren().addAll(titleLabel, cardBox);

        Scene loadingScene = new Scene(loadingBox, 800, 600);
        primaryStage.setTitle("MarketPlace FX - Cargando...");
        primaryStage.setScene(loadingScene);
        primaryStage.show();

        // Crear tarea concurrente para carga de datos
        CargaProductosTask task = new CargaProductosTask("Catálogo");

        // Vincular propiedades del Task con los componentes de la UI
        progressBar.progressProperty().bind(task.progressProperty());
        statusLabel.textProperty().bind(task.messageProperty());

        // Al completarse exitosamente, mostrar el catálogo real
        task.setOnSucceeded(e -> {
            mostrarCatalogoReal(usuario);
        });

        // Manejar errores
        task.setOnFailed(e -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error de carga");
            alert.setContentText("No se pudieron cargar los productos. Intenta de nuevo.");
            alert.showAndWait();
        });

        // Ejecutar la tarea en un hilo secundario (NO en el hilo de JavaFX)
        Thread hiloCarga = new Thread(task);
        hiloCarga.setDaemon(true); // Se cierra al cerrar la aplicación
        hiloCarga.start();
    }

    /**
     * Renderiza el catálogo de productos una vez finalizada la carga.
     */
    private void mostrarCatalogoReal(Usuario usuario) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox topBar = new HBox(15);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.setStyle("-fx-background-color: white;");

        Label welcomeLabel = new Label("Bienvenido, " + usuario.getNombre());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        cartCountLabel = new Label("Carrito (0)");
        cartCountLabel.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 20; -fx-cursor: hand;");
        cartCountLabel.setOnMouseClicked(e -> mostrarCarrito());

        Button logoutBtn = new Button("Salir");
        logoutBtn.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 20;");
        logoutBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.start(primaryStage);
        });

        topBar.getChildren().addAll(welcomeLabel, spacer, cartCountLabel, logoutBtn);

        Label titleLabel = new Label("Catálogo de Productos");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-padding: 20 0 0 30;");

        GridPane grid = new GridPane();
        grid.setHgap(20);
        grid.setVgap(20);
        grid.setPadding(new Insets(20, 30, 30, 30));

        int col = 0, row = 0;
        for (Producto p : productoController.getAllProductos()) {
            VBox card = crearTarjetaProducto(p);
            grid.add(card, col, row);
            col++;
            if (col == 3) {
                col = 0;
                row++;
            }
        }

        ScrollPane scrollPane = new ScrollPane(grid);
        scrollPane.setFitToWidth(true);

        VBox centerBox = new VBox(titleLabel, scrollPane);
        root.setTop(topBar);
        root.setCenter(centerBox);

        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setTitle("MarketPlace FX - Catálogo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private VBox crearTarjetaProducto(Producto producto) {
        VBox card = new VBox(8);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(15));
        card.setPrefWidth(280);
        card.setStyle("-fx-background-color: white; -fx-background-radius: 12; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10, 0, 0, 5);");

        Label nombreLabel = new Label(producto.getNombre());
        nombreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label precioLabel = new Label(producto.getPrecioFormateado());
        precioLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        Label stockLabel = new Label("Stock: " + producto.getStock());
        stockLabel.setStyle("-fx-font-size: 12px; -fx-text-fill: #48bb78;");

        Spinner<Integer> spinner = new Spinner<>(1, Math.max(1, producto.getStock()), 1);
        spinner.setPrefWidth(80);

        Button comprarBtn = new Button("Agregar al carrito");
        comprarBtn.setStyle("-fx-background-color: #48bb78; -fx-text-fill: white; -fx-padding: 8 15; -fx-background-radius: 20;");
        comprarBtn.setOnAction(e -> {
            carritoController.agregarProducto(producto, spinner.getValue());
            cartCountLabel.setText("Carrito (" + carritoController.getCantidadItems() + ")");

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("✓ " + producto.getNombre() + " agregado");
            alert.show();
        });

        card.getChildren().addAll(nombreLabel, precioLabel, stockLabel, spinner, comprarBtn);
        return card;
    }

    private void mostrarCarrito() {
        CarritoView carritoView = new CarritoView();
        carritoView.start(primaryStage, carritoController, this);
    }
}
