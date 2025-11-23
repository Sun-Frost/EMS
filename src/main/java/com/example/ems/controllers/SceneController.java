package com.example.ems.controllers;

import com.example.ems.views.*;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class SceneController {
    public static void switchScene(Stage stage, String sceneName, int userId, String role) {
        BorderPane rootLayout = new BorderPane();
        rootLayout.setStyle("-fx-background-color: #d9d9d9");

        SidebarView sidebarView = new SidebarView(stage, userId, role);

        switch (sceneName) {
            case "Dashboard":
                rootLayout.setCenter(new DashboardView(stage,userId));
                break;
            case "Profile":
                rootLayout.setCenter(new ProfileView(userId));
                break;
            case "Schedule":
                if (role.equals("Employee")) {
                    rootLayout.setCenter(new ScheduleView(userId));
                }
                break;
            case "Leave Request":
                if (role.equals("Employee")) {
                    rootLayout.setCenter(new LeaveRequestView(userId));
                }
                break;
            case "Employees":
                if (role.equals("Admin")) {
                    rootLayout.setCenter(new ViewAllEmployeesView(userId));
                }
                break;
            case "Leave Requests":
                if (role.equals("Admin")) {
                    rootLayout.setCenter(new ApproveRequestsView(userId));
                }
                break;
            default:
        }

        rootLayout.setLeft(sidebarView);

        Scene scene = new Scene(rootLayout, stage.getWidth(), stage.getHeight());
        stage.setScene(scene);
        stage.show();
    }
}