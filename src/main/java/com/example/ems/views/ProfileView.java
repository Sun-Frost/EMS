package com.example.ems.views;

import com.example.ems.controllers.ProfileController;
import com.example.ems.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

public class ProfileView extends VBox {
    public ProfileView(int userId) {
        ProfileController controller = new ProfileController(userId);
        User user = controller.fetchUserProfile();

        String firstName = user.getFirstName();
        String lastName = user.getLastName();
        String email = user.getEmail();
        String phone = user.getPhone();
        String role = user.getRole();
        String shiftType = user.getShiftType();
        String password = user.getPassword();

        this.setAlignment(Pos.CENTER);
        this.setSpacing(20);
        this.setPadding(new Insets(20));
        this.setStyle("-fx-background-color: #D9D9D9;");

        VBox prbox = new VBox(20);
        prbox.setAlignment(Pos.TOP_CENTER);
        prbox.setPadding(new Insets(20));
        prbox.setMaxWidth(400);
        prbox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        prbox.setEffect(new DropShadow(10, Color.GRAY));

        Text title = new Text("Profile");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web("#806885"));

        VBox userInfo = new VBox(10);
        userInfo.setAlignment(Pos.CENTER_LEFT);
        userInfo.setPadding(new Insets(10));

        Text nameText = new Text(firstName + " " + lastName);
        nameText.setFont(Font.font("Arial", FontWeight.BOLD, 20));

        Text roleText = new Text("Role: " + role);
        roleText.setFont(Font.font("Arial", 14));
        roleText.setFill(Color.GRAY);

        Text shiftText = new Text("Shift: " + shiftType);
        shiftText.setFont(Font.font("Arial", 14));
        shiftText.setFill(Color.GRAY);

        userInfo.getChildren().addAll(nameText, roleText, shiftText);

        GridPane g = new GridPane();
        g.setAlignment(Pos.CENTER);
        g.setHgap(10);
        g.setVgap(10);
        g.setPadding(new Insets(10));

        Label firstNameLabel = new Label("First Name:");
        TextField firstNameField = new TextField(firstName);
        firstNameField.setDisable(!role.equals("Admin"));
        firstNameField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label lastNameLabel = new Label("Last Name:");
        TextField lastNameField = new TextField(lastName);
        lastNameField.setDisable(!role.equals("Admin"));
        lastNameField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label emailLabel = new Label("Email:");
        TextField emailField = new TextField(email);
        emailField.setDisable(!role.equals("Admin"));
        emailField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label phoneLabel = new Label("Phone:");
        TextField phoneField = new TextField(phone);
        phoneField.setDisable(!role.equals("Admin"));
        phoneField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        Label passwordLabel = new Label("Password:");
        PasswordField passwordField = new PasswordField();
        passwordField.setText(password);
        passwordField.setDisable(!role.equals("Admin"));
        passwordField.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        g.addRow(0, firstNameLabel, firstNameField);
        g.addRow(1, lastNameLabel, lastNameField);
        g.addRow(2, emailLabel, emailField);
        g.addRow(3, phoneLabel, phoneField);
        g.addRow(4, passwordLabel, passwordField);

        Button saveBtn = new Button("Save Changes");
        saveBtn.setStyle("-fx-background-color: #806885; -fx-text-fill: white; -fx-padding: 10 20; -fx-background-radius: 10;");
        saveBtn.setDisable(!role.equals("Admin"));

        Label message = new Label();
        message.setTextFill(Color.RED);

        prbox.getChildren().addAll( userInfo, g, saveBtn, message);

        saveBtn.setOnAction(e -> {
            String newFirstName = firstNameField.getText();
            String newLastName = lastNameField.getText();
            String newEmail = emailField.getText();
            String newPhone = phoneField.getText();
            String newPassword = passwordField.getText();

            boolean success = controller.saveChanges(newFirstName, newLastName, newEmail, newPhone, newPassword);

            if (success) {
                message.setText("Changes saved successfully!");
            } else {
                message.setText("Failed to save changes. Please try again.");
            }
        });

        this.getChildren().addAll(title, prbox);
    }
}