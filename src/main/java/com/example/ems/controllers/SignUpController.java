package com.example.ems.controllers;

import com.example.ems.db.DBConnection;
import com.example.ems.views.LoginView;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SignUpController {

    public static boolean handleSignUp(String firstName, String lastName, String email, String phone, String password, String role, int departmentId, String shiftType) {
        if (emailExists(email)) {
            return false;
        }
        String query = "INSERT INTO User (firstName, lastName, email, phone, password, role, departmentId, shiftType) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, phone);
            statement.setString(5, password);
            statement.setString(6, role);
            statement.setInt(7, departmentId);
            statement.setString(8, shiftType);

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    private static boolean emailExists(String email) {
        String query = "SELECT COUNT(*) FROM User WHERE email = ?";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return false;
    }

    public static void switchToLogin(Stage stage) {
        Scene loginScene = LoginView.createLoginScene(stage);
        stage.setScene(loginScene);
        stage.show();
    }
}