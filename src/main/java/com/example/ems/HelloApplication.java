package com.example.ems;

import com.example.ems.views.*;
import javafx.application.Application;
import javafx.stage.Stage;
public class HelloApplication extends Application {
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setScene(new LoginView().createLoginScene(primaryStage));
        primaryStage.setTitle("Employee Management System");
        primaryStage.setMaximized(true);
        primaryStage.setMinWidth(1200);
        primaryStage.setMinHeight(800);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}