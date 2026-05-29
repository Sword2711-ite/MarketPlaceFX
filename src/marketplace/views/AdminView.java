package marketplace.views;

import marketplace.controllers.ProductoController;
import marketplace.models.Producto;
import marketplace.models.Usuario;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.FXCollections;

public class AdminView {
    private ProductoController productoController = new ProductoController();
    private BorderPane root;
    private Stage primaryStage;

    public void start(Stage primaryStage, Usuario usuario) {
        this.primaryStage = primaryStage;
        root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox topBar = new HBox(20);
        topBar.setAlignment(Pos.CENTER_RIGHT);
        topBar.setPadding(new Insets(15, 30, 15, 30));
        topBar.setStyle("-fx-background-color: white;");

        Label welcomeLabel = new Label("Admin: " + usuario.getNombre());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button logoutBtn = new Button("Salir");
        logoutBtn.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white; -fx-padding: 8 20; -fx-background-radius: 20;");
        logoutBtn.setOnAction(e -> {
            LoginView loginView = new LoginView();
            loginView.start(primaryStage);
        });

        topBar.getChildren().addAll(welcomeLabel, spacer, logoutBtn);

        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(30, 20, 30, 20));
        sidebar.setStyle("-fx-background-color: white;");
        sidebar.setPrefWidth(250);

        Label menuLabel = new Label("Panel Admin");
        menuLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        Button productosBtn = new Button("Ver Productos");
        productosBtn.setOnAction(e -> mostrarProductos());

        Button agregarBtn = new Button("Agregar Producto");
        agregarBtn.setOnAction(e -> mostrarFormularioAgregar());

        Button usuariosBtn = new Button("Ver Usuarios");
        usuariosBtn.setOnAction(e -> mostrarUsuarios());

        sidebar.getChildren().addAll(menuLabel, new Separator(), productosBtn, agregarBtn, usuariosBtn);

        VBox defaultContent = new VBox();
        defaultContent.setAlignment(Pos.CENTER);
        defaultContent.setPadding(new Insets(50));
        Label defaultLabel = new Label("Seleccione una opcion del menu");
        defaultLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #666;");
        defaultContent.getChildren().add(defaultLabel);

        root.setTop(topBar);
        root.setLeft(sidebar);
        root.setCenter(defaultContent);

        Scene scene = new Scene(root, 1100, 700);
        primaryStage.setTitle("MarketPlace FX - Admin");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void mostrarProductos() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(30));

        Label title = new Label("Lista de Productos");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ListView<HBox> listView = new ListView<>();
        for (Producto p : productoController.getAllProductos()) {
            HBox itemBox = new HBox(20);
            itemBox.setAlignment(Pos.CENTER_LEFT);
            itemBox.setPadding(new Insets(10));
            itemBox.setStyle("-fx-background-color: white; -fx-background-radius: 8;");

            Label infoLabel = new Label(p.getId() + " | " + p.getNombre() + " | " + p.getPrecioFormateado() + " | Stock: " + p.getStock() + " | " + p.getCategoria());
            infoLabel.setPrefWidth(500);

            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            Button editarBtn = new Button("Editar");
            editarBtn.setStyle("-fx-background-color: #4299e1; -fx-text-fill: white;");
            editarBtn.setOnAction(e -> mostrarFormularioEditar(p));

            Button eliminarBtn = new Button("Eliminar");
            eliminarBtn.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white;");
            eliminarBtn.setOnAction(e -> {
                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("¿Eliminar " + p.getNombre() + "?");
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        productoController.eliminarProducto(p.getId());
                        mostrarProductos();
                        Alert success = new Alert(Alert.AlertType.INFORMATION);
                        success.setContentText("Producto eliminado");
                        success.show();
                    }
                });
            });

            itemBox.getChildren().addAll(infoLabel, spacer, editarBtn, eliminarBtn);
            listView.getItems().add(itemBox);
        }

        content.getChildren().addAll(title, listView);
        root.setCenter(content);
    }

    private void mostrarFormularioAgregar() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(30));
        content.setMaxWidth(400);

        Label title = new Label("Agregar Producto");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField nombreField = new TextField();
        nombreField.setPromptText("Nombre");

        TextField precioField = new TextField();
        precioField.setPromptText("Precio");

        TextField stockField = new TextField();
        stockField.setPromptText("Stock");

        TextField categoriaField = new TextField();
        categoriaField.setPromptText("Categoria");

        Button guardarBtn = new Button("Guardar");
        guardarBtn.setStyle("-fx-background-color: #48bb78; -fx-text-fill: white; -fx-padding: 10 20;");
        guardarBtn.setOnAction(e -> {
            try {
                String nombre = nombreField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String categoria = categoriaField.getText();

                productoController.agregarProducto(nombre, precio, stock, categoria);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Producto agregado correctamente");
                alert.show();

                mostrarProductos();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Precio y stock deben ser numeros validos");
                alert.show();
            }
        });

        content.getChildren().addAll(title, nombreField, precioField, stockField, categoriaField, guardarBtn);
        root.setCenter(content);
    }

    private void mostrarFormularioEditar(Producto producto) {
        VBox content = new VBox(15);
        content.setPadding(new Insets(30));
        content.setMaxWidth(400);

        Label title = new Label("Editar Producto: " + producto.getNombre());
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField nombreField = new TextField(producto.getNombre());
        nombreField.setPromptText("Nombre");

        TextField precioField = new TextField(String.valueOf(producto.getPrecio()));
        precioField.setPromptText("Precio");

        TextField stockField = new TextField(String.valueOf(producto.getStock()));
        stockField.setPromptText("Stock");

        TextField categoriaField = new TextField(producto.getCategoria());
        categoriaField.setPromptText("Categoria");

        Button guardarBtn = new Button("Actualizar");
        guardarBtn.setStyle("-fx-background-color: #4299e1; -fx-text-fill: white; -fx-padding: 10 20;");
        guardarBtn.setOnAction(e -> {
            try {
                String nombre = nombreField.getText();
                double precio = Double.parseDouble(precioField.getText());
                int stock = Integer.parseInt(stockField.getText());
                String categoria = categoriaField.getText();

                productoController.actualizarProducto(producto.getId(), nombre, precio, stock, categoria);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Producto actualizado");
                alert.show();

                mostrarProductos();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Precio y stock deben ser numeros validos");
                alert.show();
            }
        });

        content.getChildren().addAll(title, nombreField, precioField, stockField, categoriaField, guardarBtn);
        root.setCenter(content);
    }

    private void mostrarUsuarios() {
        VBox content = new VBox(15);
        content.setPadding(new Insets(30));

        Label title = new Label("Usuarios Registrados");
        title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        ListView<String> listView = new ListView<>();
        listView.getItems().add("admin | Administrador (admin)");
        listView.getItems().add("juan | Cliente (juan)");
        listView.getItems().add("maria | Cliente (maria)");

        content.getChildren().addAll(title, listView);
        root.setCenter(content);
    }
}