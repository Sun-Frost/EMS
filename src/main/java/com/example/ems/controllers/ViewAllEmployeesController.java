package com.example.ems.controllers;

import com.example.ems.db.DBConnection;
import com.example.ems.models.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ViewAllEmployeesController {
    private final int adminId;
    private static Connection connection;

    public ViewAllEmployeesController(int adminId) {
        this.adminId = adminId;
        this.connection = DBConnection.getConnection();

    }

    public boolean updateEmployee(User employee) {
        String query = "UPDATE user SET firstName = ?, lastName = ?, email = ?, phone = ?, shiftType = ? WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getFirstName());
            stmt.setString(2, employee.getLastName());
            stmt.setString(3, employee.getEmail());
            stmt.setString(4, employee.getPhone());
            stmt.setString(5, employee.getShiftType());
            stmt.setInt(6, employee.getUserId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }

    public List<User> fetchEmployeesInDepartment() {
        List<User> employees = new ArrayList<>();
        String query = "SELECT userId, firstName, lastName, email, phone, shiftType, role FROM user WHERE departmentId = (SELECT departmentId FROM user WHERE userId = ?) AND role = 'Employee'";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int userId = rs.getInt("userId");
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                String email = rs.getString("email");
                String phone = rs.getString("phone");
                String role = rs.getString("role");
                String shiftType = rs.getString("shiftType");

                User user = new User();
                user.setUserId(userId);
                user.setFirstName(firstName);
                user.setLastName(lastName);
                user.setEmail(email);
                user.setPhone(phone);
                user.setRole(role);
                user.setShiftType(shiftType);

                employees.add(user);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return employees;
    }
    public static boolean deleteEmployee(int userId) {
        String query = "DELETE FROM user WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
}