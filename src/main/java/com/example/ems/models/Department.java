
package com.example.ems.models;

public class Department {
    private int departmentId;
    private String departmentName;
    private String location;
    private String managerName;
    private String managerEmail;

    public Department(int departmentId, String departmentName, String location, String managerName, String managerEmail) {
        this.departmentId = departmentId;
        this.departmentName = departmentName;
        this.location = location;
        this.managerName = managerName;
        this.managerEmail = managerEmail;
    }

    public int getDepartmentId() { return departmentId; }
    public void setDepartmentId(int departmentId) { this.departmentId = departmentId; }

    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String departmentName) { this.departmentName = departmentName; }

    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }

    public String getManagerName() { return managerName; }
    public void setManagerName(String managerName) { this.managerName = managerName; }

    public String getManagerEmail() { return managerEmail; }
    public void setManagerEmail(String managerEmail) { this.managerEmail = managerEmail; }
}
