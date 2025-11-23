package com.example.ems.views;

import com.example.ems.controllers.SignUpController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.regex.Pattern;

public class SignUpView {
    public static Scene createSignUpScene(Stage stage) {
        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        box.setStyle("-fx-background-color: #a7a3a7;");
        box.setPadding(new Insets(20));

        GridPane g = new GridPane();
        g.setAlignment(Pos.CENTER);
        g.setHgap(10);
        g.setVgap(10);
        g.setMaxWidth(400);
        g.setPadding(new Insets(20, 20, 20, 20));
        g.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: grey;");

        Label titleLabel = new Label("Sign up!");
        titleLabel.setStyle("-fx-font-size: 28px; -fx-font-weight: bold; -fx-text-fill: #333333;");
        g.add(titleLabel, 0, 0, 2, 1);

        ToggleGroup roleGroup = new ToggleGroup();
        RadioButton employeeRadio = new RadioButton("Employee");
        employeeRadio.setToggleGroup(roleGroup);
        employeeRadio.setSelected(true);
        RadioButton adminRadio = new RadioButton("Admin");
        adminRadio.setToggleGroup(roleGroup);

        HBox roleBox = new HBox(10, employeeRadio, adminRadio);
        g.add(roleBox, 0, 1, 2, 1);

        Label firstNameLabel = new Label("First Name");
        TextField firstNameField = new TextField();
        firstNameField.setPrefWidth(200);
        firstNameField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(firstNameLabel, 0, 2);
        g.add(firstNameField, 1, 2);

        Label lastNameLabel = new Label("Last Name");
        TextField lastNameField = new TextField();
        lastNameField.setPrefWidth(200);
        lastNameField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(lastNameLabel, 0, 3);
        g.add(lastNameField, 1, 3);

        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setPrefWidth(200);
        emailField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(emailLabel, 0, 4);
        g.add(emailField, 1, 4);

        Label phoneLabel = new Label("Phone Number");
        TextField phoneField = new TextField();
        phoneField.setPrefWidth(200);
        phoneField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(phoneLabel, 0, 5);
        g.add(phoneField, 1, 5);

        Label passwordLabel = new Label("Password");
        PasswordField passwordField = new PasswordField();
        passwordField.setPrefWidth(200);
        passwordField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(passwordLabel, 0, 6);
        g.add(passwordField, 1, 6);

        Label confirmPasswordLabel = new Label("Confirm Password");
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPrefWidth(200);
        confirmPasswordField.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(confirmPasswordLabel, 0, 7);
        g.add(confirmPasswordField, 1, 7);

        Label departmentLabel = new Label("Select Department");
        ComboBox<String> departmentComboBox = new ComboBox<>();
        departmentComboBox.getItems().addAll("HR", "IT", "Finance", "Operations");
        departmentComboBox.setPrefWidth(200);
        departmentComboBox.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(departmentLabel, 0, 8);
        g.add(departmentComboBox, 1, 8);

        Label shiftLabel = new Label("Select Shift Time");
        ComboBox<String> shiftComboBox = new ComboBox<>();
        shiftComboBox.getItems().addAll("Morning", "Afternoon", "Night");
        shiftComboBox.setPrefWidth(200);
        shiftComboBox.setStyle("-fx-background-radius: 5; -fx-border-radius: 5; -fx-padding: 5;");
        g.add(shiftLabel, 0, 9);
        g.add(shiftComboBox, 1, 9);

        Label message = new Label();
        message.setStyle("-fx-text-fill: black; -fx-font-size: 12px;");
        g.add(message, 0, 10, 2, 1);

        HBox btnBox = new HBox(10);
        btnBox.setAlignment(Pos.CENTER);
        Button backBtn = new Button("Back");
        backBtn.setStyle("-fx-background-color: #cccccc; -fx-text-fill: #333333; -fx-background-radius: 5; -fx-padding: 10 20;");
        backBtn.setOnAction(e -> {
            SignUpController.switchToLogin(stage);
        });

        Button signUpBtn = new Button("Sign Up");
        signUpBtn.setStyle("-fx-background-color: #806885; -fx-text-fill: white; -fx-background-radius: 5; -fx-padding: 10 20;");
        signUpBtn.setOnAction(e -> {
            message.setText("");
            message.setTextFill(Color.RED);

            String firstName = firstNameField.getText();
            String lastName = lastNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            String password = passwordField.getText();
            String confirmPassword = confirmPasswordField.getText();
            String role = employeeRadio.isSelected() ? "Employee" : "Admin";
            String department = departmentComboBox.getValue();
            String shiftType = shiftComboBox.getValue();

            if (firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || department == null || shiftType == null) {
                message.setText("All fields are required.");
                return;
            }
            if (!( firstName != null && !firstName.isEmpty() && firstName.length() <= 50)) {
               message.setText("Invalid first name.");
               return;
            }
            if (!(lastName != null && !lastName.isEmpty() && lastName.length() <= 50)) {
                message.setText("Invalid last name.");
                return;
            }
            if (!isValidEmail(email)) {
                message.setText("Invalid email.");
                return;
            }
            if (!password.equals(confirmPassword)) {
                message.setText("Passwords do not match.");
                return;
            }
            int departmentId = getDepartmentId(department);
            if (departmentId == -1) {
                message.setText("Invalid department selected.");
                return;
            }

            boolean isSuccess = SignUpController.handleSignUp(firstName, lastName, email, phone, password, role, departmentId, shiftType);
            if (isSuccess) {
                message.setText("Sign up successful!");
                SignUpController.switchToLogin(stage);
            } else {
                message.setText("Failed to sign up. Please try again.");
            }
        });

        btnBox.getChildren().addAll(backBtn, signUpBtn);
        g.add(btnBox, 0, 11, 2, 1);

        box.getChildren().add(g);

        Scene scene = new Scene(box, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        return scene;
    }
    private static boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email != null && Pattern.matches(emailRegex, email);
    }

    private static int getDepartmentId(String departmentName) {
        switch (departmentName) {
            case "HR": return 1;
            case "IT": return 2;
            case "Finance": return 3;
            case "Operations": return 4;
            default: return -1;
        }
    }
}