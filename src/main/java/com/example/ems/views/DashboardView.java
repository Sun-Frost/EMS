package com.example.ems.views;

import com.example.ems.controllers.DashboardController;
import com.example.ems.controllers.MoodController;
import com.example.ems.controllers.SceneController;
import javafx.animation.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.Arrays;

public class DashboardView extends VBox {

    private Label departmentName;
    private Label managerName;
    private Label managerEmail;
    private Label employeesnb;
    private Label moodAvg;
    private Label userName;
    private Label dhname;
    private ProgressBar moodPB;
    private int userId;

    public DashboardView(Stage stage, int userId) {
        this.userId = userId;

        HBox top = new HBox();
        top.setStyle("-fx-background-color: #a7a3a7;");
        top.setPadding(new Insets(10));
        top.setPrefHeight(50);
        userName = new Label("User’s Name");
        userName.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        ImageView profileIcon = new ImageView(new Image("file:src/main/resources/profile.png"));
        profileIcon.setFitWidth(20);
        profileIcon.setFitHeight(20);
        profileIcon.setOnMouseClicked(e -> {
            String role = DashboardController.getUserRole(userId);
            SceneController.switchScene(stage, "Profile", userId, role);
        });
        HBox ubox = new HBox(userName, profileIcon);
        ubox.setSpacing(5);
        ubox.setAlignment(Pos.CENTER_RIGHT);
        HBox.setHgrow(ubox, Priority.ALWAYS);
        top.getChildren().add(ubox);

        VBox dbox = new VBox(10);
        dbox.setPadding(new Insets(20));
        dbox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        dbox.setEffect(new DropShadow(10, Color.GRAY));
        departmentName = new Label("Department Name");
        departmentName.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        managerName = new Label("Manager’s Name");
        managerName.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        managerEmail = new Label("Manager’s Email");
        managerEmail.setFont(Font.font("Arial", 18));
        dbox.getChildren().addAll(departmentName, managerName, managerEmail);

        VBox eBox = new VBox(10);
        eBox.setPadding(new Insets(20));
        eBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        eBox.setEffect(new DropShadow(10, Color.GRAY));
        ImageView employeesIcon = new ImageView(new Image("file:src/main/resources/employees.png"));
        employeesIcon.setFitWidth(70);
        employeesIcon.setFitHeight(70);
        employeesnb = new Label("Number of employees in\nthe department");
        employeesnb.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        employeesnb.setAlignment(Pos.CENTER);
        employeesnb.setWrapText(true);
        eBox.getChildren().addAll(employeesIcon, employeesnb);
        eBox.setAlignment(Pos.CENTER);

        VBox mBox = new VBox(10);
        mBox.setAlignment(Pos.CENTER);
        mBox.setPadding(new Insets(20));
        mBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        mBox.setEffect(new DropShadow(10, Color.GRAY));
        Label mood = new Label("Department’s Mood Average");
        mood.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        moodPB = new ProgressBar();
        moodPB.setPrefWidth(350);
        moodPB.setStyle("-fx-accent: #806885;");
        moodAvg = new Label("3.0");
        moodAvg.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        Image moodsEmojis = new Image("file:src/main/resources/moodemojis.png");
        ImageView moodImage = new ImageView(moodsEmojis);
        moodImage.setFitWidth(420);
        moodImage.setFitHeight(110);
        HBox mrBox = new HBox(10);
        mrBox.setAlignment(Pos.CENTER);
        for (int i = 1; i <= 5; i++) {
            Button moodBtn = new Button(String.valueOf(i));
            moodBtn.setStyle("-fx-background-color: #806885; -fx-text-fill: white; -fx-font-size: 16px; -fx-padding: 10 20; -fx-background-radius: 10;");
            int rating = i;
            moodBtn.setOnAction(e -> updateMoodRating(rating));
            mrBox.getChildren().add(moodBtn);
        }
        mBox.getChildren().addAll(mood, moodPB, moodAvg, mrBox, moodImage);

        VBox hapBox = new VBox(10);
        hapBox.setAlignment(Pos.CENTER);
        hapBox.setPadding(new Insets(20));
        hapBox.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        hapBox.setEffect(new DropShadow(10, Color.GRAY));
        Label hTitle = new Label("Happiest Department of the week:");
        hTitle.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        hTitle.setTextFill(Color.web("#806885"));

        Image hImage = new Image("file:src/main/resources/happy.png");
        ImageView hImageView = new ImageView(hImage);
        hImageView.setFitWidth(70);
        hImageView.setFitHeight(70);

        dhname = new Label();
        dhname.setFont(Font.font("Arial", FontWeight.BOLD, 16));
        hapBox.getChildren().addAll(hImageView, hTitle, dhname);
        String happiestDept = Arrays.toString(DashboardController.getHappiestDepartment());
        dhname.setText(happiestDept.isEmpty() ? "Not available" : happiestDept);

        FadeTransition fadeIn = new FadeTransition(Duration.seconds(2), hapBox);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();

        HBox topSection = new HBox(20);
        topSection.setPadding(new Insets(20));
        topSection.setAlignment(Pos.CENTER_LEFT);
        topSection.getChildren().addAll(dbox, eBox);
        HBox.setHgrow(dbox, Priority.ALWAYS);
        HBox.setHgrow(eBox, Priority.ALWAYS);

        HBox bottomSection = new HBox(20);
        bottomSection.setPadding(new Insets(20));
        bottomSection.setAlignment(Pos.CENTER_LEFT);
        bottomSection.getChildren().addAll(mBox, hapBox);
        HBox.setHgrow(mBox, Priority.ALWAYS);
        HBox.setHgrow(hapBox, Priority.ALWAYS);
        
        VBox main = new VBox(30, top, topSection, bottomSection);
        main.setPadding(new Insets(20));
        main.setStyle("-fx-background-color: #d9d9d9;");

        getChildren().add(main);

        displayData();
    }

    private void displayData() {
        String[] departmentInfo = DashboardController.fetchDepartmentDetails(userId);
        if (departmentInfo[0] != null) {
            departmentName.setText(departmentInfo[0]);
            managerName.setText("Manager: " + departmentInfo[1]);
            managerEmail.setText("Email: " + departmentInfo[2]);
            userName.setText(departmentInfo[3] +" "+  departmentInfo[4]);
        }

        int employeeCount= DashboardController.fetchEmployeeCount(userId);
        employeesnb.setText("Number of employees: " + employeeCount);

        double moodAverage = DashboardController.fetchMoodAverage(userId);
        updateMoodAvg(moodAverage);
    }

    private void updateMoodAvg(double moodAverage) {
        moodAvg.setText(String.format("%.1f", moodAverage));
        if (moodAverage >=0 && moodAverage<2)
            moodAvg.setTextFill(Color.RED);
        else if (moodAverage >= 2 && moodAverage <3) {
            moodAvg.setTextFill(Color.ORANGE);
        }
        else if (moodAverage >= 3 && moodAverage <4) {
            moodAvg.setTextFill(Color.YELLOW);
        }
        else if (moodAverage >= 4 && moodAverage <=5) {
            moodAvg.setTextFill(Color.GREEN);
        }
        double progress = (moodAverage - 1) / 4.0;
        moodPB.setProgress(progress);
        String happiestDept = Arrays.toString(DashboardController.getHappiestDepartment());
        dhname.setText(happiestDept.isEmpty() ? "Not available" : happiestDept);
    }

    private void updateMoodRating(int rating) {
        if (MoodController.userVotedToday(userId)) {
            showAlert("Already Voted", "You have already rated your mood today.");
            return;
        }

        boolean isSuccess = MoodController.submitMood(userId, rating);

        if (isSuccess) {
            double newMoodAverage = DashboardController.fetchMoodAverage(userId);
            updateMoodAvg(newMoodAverage);
            showAlert("Success", "Your mood rating has been submitted.");
        } else {
            showAlert("Error", "Failed to submit mood rating. Please try again.");
        }

    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}