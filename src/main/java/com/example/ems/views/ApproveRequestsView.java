package com.example.ems.views;

import com.example.ems.controllers.ApproveRequestsController;
import com.example.ems.models.LeaveRequest;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class ApproveRequestsView extends StackPane {
    public ApproveRequestsView(int adminId) {
        VBox main = new VBox(20);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(20));
        main.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: grey;");

        Text title = new Text("Approve Leave Requests");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        title.setFill(Color.web("#806885"));

        TableView<LeaveRequest> leaveRequestTable = new TableView<>();
        leaveRequestTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        leaveRequestTable.setStyle("-fx-background-color: white; -fx-font-size: 16px; -fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");
        leaveRequestTable.setEffect(new DropShadow(10, Color.GRAY));

        TableColumn<LeaveRequest, String> employeeNameCol = new TableColumn<>("Employee Name");
        employeeNameCol.setCellValueFactory(param -> {
            LeaveRequest leaveRequest = param.getValue();
            ApproveRequestsController controller = new ApproveRequestsController(adminId);
            String employeeName = controller.getEmployeeName(leaveRequest.getUserId());
            return new javafx.beans.property.SimpleStringProperty(employeeName);
        });

        TableColumn<LeaveRequest, String> leaveDateCol = new TableColumn<>("Leave Date");
        leaveDateCol.setCellValueFactory(new PropertyValueFactory<>("leaveDay"));

        TableColumn<LeaveRequest, String> reasonCol = new TableColumn<>("Reason");
        reasonCol.setCellValueFactory(new PropertyValueFactory<>("reason"));

        //reference: https://codingtechroom.com/question/javafx-tableview-custom-cell-factories
        TableColumn<LeaveRequest, Void> actionCol = new TableColumn<>("Action");
        actionCol.setCellFactory(param -> new TableCell<>() {
            private final Button approveButton = new Button("Approve");
            private final Button declineButton = new Button("Decline");
            private final HBox btnBox = new HBox(10, approveButton, declineButton);

            {
                approveButton.setOnAction(e -> handleAction(1));
                declineButton.setOnAction(e -> handleAction(-1));
            }

            private void handleAction(int status) {
                LeaveRequest leaveRequest = getTableView().getItems().get(getIndex());
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirm Action");
                alert.setHeaderText(null);
                alert.setContentText(status == 1 ? "Are you sure you want to approve this leave request?" : "Are you sure you want to decline this leave request?");

                alert.showAndWait().ifPresent(response -> {
                    if (response == ButtonType.OK) {
                        ApproveRequestsController controller = new ApproveRequestsController(adminId);
                        boolean isSuccess = controller.updateLeaveRequestStatus(leaveRequest.getLeaveRequestId(), status);

                        if (isSuccess) {
                            ObservableList<LeaveRequest> updatedLeaveRequests = FXCollections.observableArrayList(controller.fetchLeaveRequests());
                            leaveRequestTable.setItems(updatedLeaveRequests);
                        }
                    }
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(btnBox);
                }
            }
        });

        leaveRequestTable.getColumns().addAll(employeeNameCol, leaveDateCol, reasonCol, actionCol);

        ApproveRequestsController controller = new ApproveRequestsController(adminId);
        ObservableList<LeaveRequest> leaveRequests = FXCollections.observableArrayList(controller.fetchLeaveRequests());

        leaveRequestTable.setItems(leaveRequests);
            double h = leaveRequestTable.getItems().size() * 100 + 100;
            leaveRequestTable.setPrefHeight(h);

            double w = leaveRequestTable.getColumns().size() * 310;
            leaveRequestTable.setPrefWidth(w);

            leaveRequestTable.setMaxHeight(h);
            leaveRequestTable.setMaxWidth(w);

        main.getChildren().addAll(title, leaveRequestTable);

        getChildren().add(main);
        StackPane.setAlignment(main, Pos.CENTER);
    }
}