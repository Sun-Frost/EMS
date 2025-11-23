package com.example.ems.controllers;

import com.example.ems.db.DBConnection;
import com.example.ems.models.LeaveRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ApproveRequestsController {
    private final int adminId;
    private Connection connection;

    public ApproveRequestsController(int adminId) {
        this.adminId = adminId;
        this.connection = DBConnection.getConnection();
    }

    public List<LeaveRequest> fetchLeaveRequests() {
        List<LeaveRequest> leaveRequests = new ArrayList<>();
        String query = "select l.leaveRequestId, l.userId, l.leaveDay, l.reason, l.leaveStatus, u.firstName, u.lastName " +
                "FROM leaverequest l " +
                "JOIN user u ON l.userId = u.userId " +
                "WHERE u.departmentId = (SELECT departmentId FROM user WHERE userId = ?) AND u.role = 'Employee' AND l.leaveStatus = 0";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, adminId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int leaveRequestId = rs.getInt("leaveRequestId");
                int userId = rs.getInt("userId");
                String leaveDay = rs.getString("leaveDay");
                String reason = rs.getString("reason");
                int leaveStatus = rs.getInt("leaveStatus");

                LeaveRequest leaveRequest = new LeaveRequest(userId, leaveStatus, leaveDay, reason);
                leaveRequest.setLeaveRequestId(leaveRequestId);
                leaveRequests.add(leaveRequest);
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return leaveRequests;
    }


    public String getEmployeeName(int userId) {
        String query = "SELECT firstName, lastName FROM user WHERE userId = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String firstName = rs.getString("firstName");
                String lastName = rs.getString("lastName");
                return firstName + " " + lastName;
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        return "Unknown";
    }

    public boolean updateLeaveRequestStatus(int leaveRequestId, int leaveStatus) {
        String query = "UPDATE leaverequest SET leaveStatus = ? WHERE leaveRequestId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, leaveStatus);
            stmt.setInt(2, leaveRequestId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {
            return false;
        }
    }
}