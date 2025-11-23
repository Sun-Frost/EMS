package com.example.ems.controllers;

import com.example.ems.db.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ScheduleController {
    private final int userId;
    private Connection connection;

    public ScheduleController(int userId) {
        this.userId = userId;
        this.connection = DBConnection.getConnection();
    }

    private String getEmployeeShiftType() {
        String shiftType = null;
        String query = "SELECT shiftType FROM user WHERE userId = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                shiftType = rs.getString("shiftType");
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        return shiftType;
    }

    private List<LocalDate> getApprovedLeaveRequests() {
        List<LocalDate> leaveRequests = new ArrayList<>();
        String query = "SELECT leaveDay FROM leaverequest WHERE userId = ? AND leaveStatus = 1";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                leaveRequests.add(rs.getDate("leaveDay").toLocalDate());
            }
        } catch (Exception e) {
            System.out.print(e.toString());
        }

        return leaveRequests;
    }


    public Map<LocalDate, String> getUpcomingWeekSchedule() {
        Map<LocalDate, String> scheduleMap = new HashMap<>();
        LocalDate today = LocalDate.now();

        String shiftType = getEmployeeShiftType();

        List<LocalDate> leaveRequests = getApprovedLeaveRequests();

        for (int i = 0; i < 7; i++) {
            LocalDate date = today.plusDays(i);
            if (leaveRequests.contains(date)) {
                scheduleMap.put(date, "DAY OFF");
            } else {
                scheduleMap.put(date, shiftType);
            }

        }

        return scheduleMap;
    }
}