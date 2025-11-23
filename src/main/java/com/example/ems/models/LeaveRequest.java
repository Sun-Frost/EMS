package com.example.ems.models;

public class LeaveRequest {
    private int leaveRequestId;
    private int userId;
    private int leaveStatus; // 0 = pending, 1 = approved, -1 = declined
    private String leaveDay;
    private String reason;

    public LeaveRequest() {}

    public LeaveRequest(int userId, int leaveStatus, String leaveDay, String reason) {
        this.userId = userId;
        this.leaveStatus = leaveStatus;
        this.leaveDay = leaveDay;
        this.reason = reason;
    }

    public int getLeaveRequestId() { return leaveRequestId; }
    public void setLeaveRequestId(int leaveRequestId) { this.leaveRequestId = leaveRequestId; }

    public int getUserId() { return userId; }
    public void setUserId(int userId) { this.userId = userId; }

    public int getLeaveStatus() { return leaveStatus; }
    public void setLeaveStatus(int leaveStatus) { this.leaveStatus = leaveStatus; }

    public String getLeaveDay() { return leaveDay; }
    public void setLeaveDay(String leaveDay) { this.leaveDay = leaveDay; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }
}