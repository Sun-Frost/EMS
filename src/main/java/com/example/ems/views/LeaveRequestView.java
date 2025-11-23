package com.example.ems.views;

import com.example.ems.controllers.LeaveRequestController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;

public class LeaveRequestView extends StackPane {
    public LeaveRequestView(int userId) {
      VBox vb = new VBox(20);
        vb.setAlignment(Pos.CENTER);
        vb.setPadding(new Insets(20));
        vb.setStyle("-fx-background-color: #D9D9D9;");

        VBox box = new VBox(20);
        box.setAlignment(Pos.TOP_CENTER);
        box.setPadding(new Insets(20));
        box.setMaxWidth(400);
        box.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        box.setEffect(new DropShadow(10, Color.GRAY));


        Text title = new Text("Submit Leave Request");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web("#806885"));

        Label pickDate = new Label("Pick a Date:");
        pickDate.setFont(Font.font("Arial", 16));
        pickDate.setTextFill(Color.BLACK);

        DatePicker dp = new DatePicker();
        dp.setPromptText("Select Leave Date");
        dp.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        TextArea reasonTxtA = new TextArea();
        reasonTxtA.setPromptText("Enter Reason");
        reasonTxtA.setMaxWidth(200);
        reasonTxtA.setMaxHeight(100);
        reasonTxtA.setStyle("-fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        Button submitBtn = new Button("Submit");
        submitBtn.setStyle("-fx-background-color: #806885; -fx-text-fill: white; -fx-padding: 5 10; -fx-background-radius: 10;");

        Label message = new Label();
        message.setStyle("-fx-text-fill: red;");

        box.getChildren().addAll(pickDate, dp,reasonTxtA, submitBtn, message);

        vb.getChildren().addAll(title, box);


        submitBtn.setOnAction(e -> {
            LocalDate leaveDate = dp.getValue();
            String reason = reasonTxtA.getText();

            if (leaveDate == null || reason.isEmpty()) {
                message.setText("Please fill in all fields.");
                return;
            }

            LeaveRequestController controller = new LeaveRequestController(userId);
            boolean success= controller.submitLeaveRequest(leaveDate, reason);

            if (success) {
                message.setTextFill(Color.GREEN);
                message.setText("Leave request submitted successfully!");
                dp.setValue(null);
                reasonTxtA.clear();
            } else {
                message.setTextFill(Color.RED);
                message.setText("Failed to submit leave request.");
            }
        });

        getChildren().addAll(vb);
        StackPane.setAlignment(vb, Pos.CENTER);
    }
}