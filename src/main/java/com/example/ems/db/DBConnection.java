package com.example.ems.db;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/employeemanagementsystem";
    private static final String USER = "farah";
    private static final String PASSWORD = "mySQLfarah2024!";

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (Exception e) {
            System.out.println(e.toString());
            return null;
        }
    }

}
