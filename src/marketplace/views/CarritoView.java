package marketplace.views;

import marketplace.controllers.CarritoController;
import marketplace.controllers.ProductoController;
import marketplace.models.ItemCarrito;
import marketplace.models.Producto;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class CarritoView {
    private ProductoController productoController = new ProductoController();

    public void start(Stage stage, CarritoController carritoController, CatalogoView catalogoView) {
        BorderPane root = new BorderPane();
        root.setStyle("-fx-background-color: #f5f5f5;");

        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        header.setPadding(new Insets(15, 30, 15, 30));
        header.setStyle("-fx-background-color: white;");

        Button backBtn = new Button("← Volver");
        backBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #667eea;");
        backBtn.setOnAction(e -> catalogoView.start(stage, carritoController.getCarrito().getUsuario()));

        Label titleLabel = new Label("Mi Carrito");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        header.getChildren().addAll(backBtn, spacer, titleLabel);

        VBox content = new VBox(20);
        content.setPadding(new Insets(30));

        if (carritoController.getItems().isEmpty()) {
            Label emptyLabel = new Label("Tu carrito está vacío");
            emptyLabel.setStyle("-fx-font-size: 18px;");
            content.getChildren().add(emptyLabel);
        } else {
            VBox itemsBox = new VBox(10);
            boolean stockValido = true;

            for (ItemCarrito item : carritoController.getItems()) {
                itemsBox.getChildren().add(crearItemCarrito(item, carritoController, stage, catalogoView));
                // Verificar stock
                Producto p = item.getProducto();
                Producto pActualizado = productoController.getProducto(p.getId());
                if (pActualizado.getStock() < item.getCantidad()) {
                    stockValido = false;
                }
            }

            if (!stockValido) {
                Label warningLabel = new Label("⚠ Algunos productos tienen stock insuficiente. Actualiza tu carrito.");
                warningLabel.setStyle("-fx-text-fill: #e53e3e; -fx-font-weight: bold;");
                itemsBox.getChildren().add(0, warningLabel);
            }

            HBox totalBox = new HBox(20);
            totalBox.setAlignment(Pos.CENTER_RIGHT);
            totalBox.setPadding(new Insets(20));
            totalBox.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

            Label totalLabel = new Label("Total:");
            totalLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

            Label totalMonto = new Label(String.format("$%.2f", carritoController.getTotal()));
            totalMonto.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

            Button comprarBtn = new Button("Finalizar Compra");
            comprarBtn.setStyle("-fx-background-color: #48bb78; -fx-text-fill: white; -fx-padding: 12 30; -fx-background-radius: 30;");

            // Verificar stock antes de comprar
            boolean finalStockValido = stockValido;
            comprarBtn.setOnAction(e -> {
                if (!finalStockValido) {
                    Alert error = new Alert(Alert.AlertType.ERROR);
                    error.setContentText("No se puede completar la compra. Algunos productos no tienen stock suficiente.");
                    error.show();
                    return;
                }

                Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
                confirm.setContentText("Total: $" + String.format("%.2f", carritoController.getTotal()) + "\n¿Confirmar compra?");
                confirm.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        if (carritoController.realizarCompra(productoController)) {
                            Alert success = new Alert(Alert.AlertType.INFORMATION);
                            success.setContentText("¡Compra realizada con éxito!");
                            success.show();
                            catalogoView.start(stage, carritoController.getCarrito().getUsuario());
                        } else {
                            Alert error = new Alert(Alert.AlertType.ERROR);
                            error.setContentText("Error al procesar la compra. Stock insuficiente.");
                            error.show();
                        }
                    }
                });
            });

            Region spacer2 = new Region();
            HBox.setHgrow(spacer2, Priority.ALWAYS);

            totalBox.getChildren().addAll(totalLabel, totalMonto, spacer2, comprarBtn);
            content.getChildren().addAll(itemsBox, totalBox);
        }

        root.setTop(header);
        root.setCenter(new ScrollPane(content));

        Scene scene = new Scene(root, 1100, 700);
        stage.setTitle("MarketPlace FX - Carrito");
        stage.setScene(scene);
        stage.show();
    }

    private HBox crearItemCarrito(ItemCarrito item, CarritoController controller, Stage stage, CatalogoView catalogoView) {
        HBox itemBox = new HBox(20);
        itemBox.setAlignment(Pos.CENTER_LEFT);
        itemBox.setPadding(new Insets(15));
        itemBox.setStyle("-fx-background-color: white; -fx-background-radius: 12;");

        Producto p = item.getProducto();
        Producto pActualizado = productoController.getProducto(p.getId());
        boolean stockInsuficiente = pActualizado.getStock() < item.getCantidad();

        VBox infoBox = new VBox(5);
        Label nombreLabel = new Label(p.getNombre());
        nombreLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label precioLabel = new Label(p.getPrecioFormateado() + " c/u");
        infoBox.getChildren().addAll(nombreLabel, precioLabel);

        if (stockInsuficiente) {
            Label stockLabel = new Label("Stock disponible: " + pActualizado.getStock());
            stockLabel.setStyle("-fx-text-fill: #e53e3e; -fx-font-size: 12px;");
            infoBox.getChildren().add(stockLabel);
        }

        Label subtotalLabel = new Label(String.format("$%.2f", item.getSubtotal()));
        subtotalLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #667eea;");

        Button eliminarBtn = new Button("Eliminar");
        eliminarBtn.setStyle("-fx-background-color: #e53e3e; -fx-text-fill: white;");
        eliminarBtn.setOnAction(e -> {
            controller.eliminarProducto(p);
            CarritoView carritoView = new CarritoView();
            carritoView.start(stage, controller, catalogoView);
        });

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        itemBox.getChildren().addAll(infoBox, spacer, subtotalLabel, eliminarBtn);
        return itemBox;
    }
}