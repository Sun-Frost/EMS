package com.example.ems.controllers;

import com.example.ems.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;

public class LeaveRequestController {
    private final int userId;
    private Connection connection;

    public LeaveRequestController(int userId) {
        this.userId = userId;
        this.connection = DBConnection.getConnection();
    }

    public boolean submitLeaveRequest(LocalDate leaveDate, String reason) {
        if (leaveDate.isBefore(LocalDate.now())) {
            System.out.println("Cannot submit leave request for past dates");
            return false;
        }
        if (hasExistingRequestForDate(leaveDate)) {
            System.out.println("You already have a leave request for this date");
            return false;
        }
        String query = "INSERT INTO leaverequest (userId, leaveStatus, leaveDay, reason) VALUES (?, ?, ?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, 0);
            stmt.setDate(3, java.sql.Date.valueOf(leaveDate));
            stmt.setString(4, reason);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            System.out.println(e.toString());
            return false;
        }
    }
    private boolean hasExistingRequestForDate(LocalDate date) {
        String query = "SELECT COUNT(*) FROM leaverequest WHERE userId = ? AND leaveDay = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            stmt.setDate(2, java.sql.Date.valueOf(date));

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (Exception e) {
            System.out.println("Error checking existing requests: " + e.toString());
        }
        return false;
    }
}