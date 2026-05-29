package marketplace.views;

import marketplace.controllers.UsuarioController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class LoginView {

    public void start(Stage primaryStage) {
        UsuarioController usuarioController = new UsuarioController();

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.setStyle("-fx-background-color: linear-gradient(to bottom, #667eea, #764ba2);");

        Label titleLabel = new Label("MarketPlace FX");
        titleLabel.setStyle("-fx-font-size: 36px; -fx-font-weight: bold; -fx-text-fill: white;");

        VBox formBox = new VBox(15);
        formBox.setAlignment(Pos.CENTER);
        formBox.setMaxWidth(350);
        formBox.setStyle("-fx-background-color: white; -fx-background-radius: 15; -fx-padding: 30;");

        Label loginLabel = new Label("Iniciar Sesion");
        loginLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Usuario");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Contraseña");

        Button loginButton = new Button("Ingresar");
        loginButton.setStyle("-fx-background-color: #667eea; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> {
            if (usuarioController.login(usernameField.getText(), passwordField.getText())) {
                if (usuarioController.isAdmin()) {
                    AdminView adminView = new AdminView();
                    adminView.start(primaryStage, usuarioController.getUsuarioActual());
                } else {
                    CatalogoView catalogoView = new CatalogoView();
                    catalogoView.start(primaryStage, usuarioController.getUsuarioActual());
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Usuario o contraseña incorrectos");
                alert.show();
            }
        });

        formBox.getChildren().addAll(loginLabel, usernameField, passwordField, loginButton);
        root.getChildren().addAll(titleLabel, formBox);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setTitle("MarketPlace FX - Login");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}