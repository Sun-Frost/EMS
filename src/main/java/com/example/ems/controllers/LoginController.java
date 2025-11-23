package com.example.ems.controllers;

import com.example.ems.db.DBConnection;
import com.example.ems.models.User;
import com.example.ems.views.SignUpView;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    public static String handleLogin(TextField usernameField, PasswordField passwordField, Stage stage) {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (username.isEmpty() || password.isEmpty()) {
            return "Email and password cannot be empty.";
        }

        User authenticatedUser = authenticateUser(username, password);

        if (authenticatedUser != null) {
            SceneController.switchScene(stage, "Dashboard", authenticatedUser.getUserId(), authenticatedUser.getRole());
            stage.show();
        }
        return "Invalid email or password.";
    }

    private static User authenticateUser(String email, String password) {

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT * FROM User WHERE email = ? AND password = ?")) {
            statement.setString(1, email);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                User user = new User();
                user.setUserId(resultSet.getInt("userId"));
                user.setFirstName(resultSet.getString("firstName"));
                user.setLastName(resultSet.getString("lastName"));
                user.setEmail(resultSet.getString("email"));
                user.setPhone(resultSet.getString("phone"));
                user.setRole(resultSet.getString("role"));
                user.setDepartmentId(resultSet.getInt("departmentId"));
                user.setShiftType(resultSet.getString("shiftType"));
                return user;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return null;
    }

    public static void switchToSignUp(Stage stage) {
        Scene signUpScene = SignUpView.createSignUpScene(stage);
        stage.setScene(signUpScene);
        stage.show();
    }
}