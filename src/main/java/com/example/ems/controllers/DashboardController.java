package com.example.ems.controllers;

import com.example.ems.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DashboardController {

    public static String[] fetchDepartmentDetails(int userId) {
        String[] departmentDetails = new String[5];

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT d.departmentName, d.managerName, d.managerEmail, u.firstName, u.lastName " +
                     "FROM User u " +
                     "JOIN Department d ON u.departmentId = d.departmentId " +
                     "WHERE u.userId = ?")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                departmentDetails[0] = resultSet.getString("departmentName");
                departmentDetails[1] = resultSet.getString("managerName");
                departmentDetails[2] = resultSet.getString("managerEmail");
                departmentDetails[3] = resultSet.getString("firstName");
                departmentDetails[4] = resultSet.getString("lastName");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return departmentDetails;
    }

    public static int fetchEmployeeCount(int userId) {
        int employeeCount = 0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS employeeCount " +
                     "FROM User u " +
                     "WHERE u.departmentId = (SELECT departmentId FROM User WHERE userId = ?) " +
                     "AND u.role = 'Employee'")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                employeeCount = resultSet.getInt("employeeCount");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return employeeCount;
    }

    public static double fetchMoodAverage(int userId) {
        double moodAverage = 0.0;

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT AVG(m.moodRating) AS moodAverage " +
                     "FROM Mood m " +
                     "JOIN User u ON m.userId = u.userId " +
                     "WHERE u.departmentId = (SELECT departmentId FROM User WHERE userId = ?)")) {
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                moodAverage = resultSet.getDouble("moodAverage");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return moodAverage;
    }

    public static String getUserRole(int userId) {
        String role = "";
        try( Connection connection= DBConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT u.role AS role FROM User u WHERE u.userId = ?")){
            statement.setInt(1, userId);
            ResultSet resultSet= statement.executeQuery();

            if( resultSet.next()){
                role= resultSet.getString("role");
            }
        }catch (Exception e){
            System.out.println(e.toString());
        }
        return role;
    }

    public static String[] getHappiestDepartment() {
        String[] departmentInfo = new String[2];

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT d.departmentName, AVG(m.moodRating) as avgMood " +
                             "FROM Mood m " +
                             "JOIN User u ON m.userId = u.userId " +
                             "JOIN Department d ON u.departmentId = d.departmentId " +
                             "WHERE m.date >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) " +
                             "GROUP BY d.departmentId " +
                             "ORDER BY avgMood DESC " +
                             "LIMIT 1")) {

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                departmentInfo[0] = resultSet.getString("departmentName");
                departmentInfo[1] = String.format("%.1f", resultSet.getDouble("avgMood"));
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return departmentInfo;
    }
}