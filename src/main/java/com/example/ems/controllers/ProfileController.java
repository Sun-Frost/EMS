package com.example.ems.controllers;

import com.example.ems.db.DBConnection;
import com.example.ems.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ProfileController {
    private final int userId;
    private Connection connection;

    public ProfileController(int userId) {
        this.userId = userId;
        this.connection = DBConnection.getConnection();
    }

    public User fetchUserProfile() {
        String query = "SELECT firstName, lastName, email, phone, role, departmentId, password, shiftType FROM user WHERE userId = ?";
        User userProfile = null;

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");
                int departmentId = rs.getInt("departmentId");
                String password = rs.getString("password");
                String shiftType = rs.getString("shiftType");

                userProfile = new User(firstName, lastName, email, phone, role, departmentId, password, shiftType);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return userProfile;
    }

    public boolean updateName(String firstName, String lastName) {
        String query = "UPDATE user SET firstName = ?, lastName = ? WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setInt(3, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean updateEmail(String email) {
        String query = "UPDATE user SET email = ? WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.print(e.toString());
            return false;
        }
    }

    public boolean updatePhone(String phone) {
        String query = "UPDATE user SET phone = ? WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, phone);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean updatePassword(String password) {
        String query = "UPDATE user SET password = ? WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, password);
            stmt.setInt(2, userId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public boolean saveChanges(String firstName, String lastName, String email, String phone, String password) {
        boolean nameUpdated = updateName(firstName, lastName);
        boolean emailUpdated = updateEmail(email);
        boolean phoneUpdated = updatePhone(phone);
        boolean passwordUpdated = updatePassword(password);

        return nameUpdated && emailUpdated && phoneUpdated && passwordUpdated;
    }
}