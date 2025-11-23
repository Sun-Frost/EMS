package com.example.ems.views;

import com.example.ems.controllers.LoginController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class LoginView {

    public static Scene createLoginScene(Stage stage) {
        Image backgroundImage = new Image("file:src/main/resources/backgroundlogin.png");
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setFitWidth(800);
        backgroundView.setFitHeight(500);
        backgroundView.setPreserveRatio(false);

        GridPane g = new GridPane();
        g.setAlignment(Pos.CENTER);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(20));
        g.setMaxWidth(300);
        g.setMaxHeight(300);
        g.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: grey;");

        Label welcome = new Label("Welcome Back!");
        welcome.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        g.add(welcome, 0, 0, 2, 1);

        Label instruction = new Label("Please enter your login details.");
        instruction.setStyle("-fx-font-size: 14px; -fx-text-fill: #333333;");
        g.add(instruction, 0, 1, 2, 1);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        emailField.setMaxWidth(200);
        emailField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");
        g.add(emailField, 0, 2, 2, 1);

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(200);
        passwordField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");
        g.add(passwordField, 0, 3, 2, 1);

        Button loginBtn = new Button("Login");
        Label message = new Label();
        HBox hbox = new HBox(5, loginBtn, message);
        hbox.setAlignment(Pos.CENTER);

        loginBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 10;");
        loginBtn.setOnAction(e -> {
            message.setTextFill(Color.RED);
            message.setText(LoginController.handleLogin(emailField, passwordField, stage));
        });

        g.add(hbox, 0, 4, 2, 1);

        Label noAccount = new Label("No Account?");
        Button signUpBtn = new Button("Sign up");
        signUpBtn.setStyle("-fx-background-color: #806885; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 10;");
        signUpBtn.setOnAction(e -> LoginController.switchToSignUp(stage));

        HBox sBox = new HBox(5, noAccount, signUpBtn);
        sBox.setAlignment(Pos.CENTER);
        g.add(sBox, 0, 5, 2, 1);

        StackPane main = new StackPane();
        main.getChildren().addAll(backgroundView, g);
        StackPane.setAlignment(g, Pos.CENTER);

        Scene scene = new Scene(main, stage.getWidth(), stage.getHeight());
        stage.setTitle("Login Page");

        scene.widthProperty().addListener((obs, oldVal, newVal) -> {
            backgroundView.setFitWidth(newVal.doubleValue());
        });

        scene.heightProperty().addListener((obs, oldVal, newVal) -> {
            backgroundView.setFitHeight(newVal.doubleValue());
        });

        return scene;
    }
}
