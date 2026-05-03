package marketplace.views;

import marketplace.controllers.*;
import marketplace.models.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.lang.classfile.Label;

public class CatalogoView {
    private ProductoController productoController = new ProductoController();
    private CarritoController carritoController;
    private Stage primaryStage;
    private Label cartCountLabel;
    
    public void start(Stage stage, Usuario usuario) {
        this.primaryStage = stage;
        this.carritoController = new CarritoController(usuario);
        
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
        
        Label titleLabel = new Label("Catalogo de Productos");
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
        primaryStage.setTitle("MarketPlace FX - Catalogo");
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