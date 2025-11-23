package com.example.ems.views;

import com.example.ems.controllers.ViewAllEmployeesController;
import com.example.ems.models.User;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.List;

public class ViewAllEmployeesView extends StackPane {
    private GridPane gp;
    private List<User> employees;
    private ViewAllEmployeesController controller;

    public ViewAllEmployeesView(int adminId) {
        VBox main = new VBox(20);
        main.setAlignment(Pos.CENTER);
        main.setPadding(new Insets(20));
        main.setStyle("-fx-background-color: #D9D9D9; -fx-background-radius: 15; -fx-border-radius: 15; -fx-border-color: #E0E0E0;");

        Text title = new Text("All Employees");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web("#806885"));

        TextField searchBar = new TextField();
        searchBar.setPromptText("Search by first or last name...");
        searchBar.setStyle("-fx-background-color: white; -fx-background-radius: 5; -fx-border-radius: 5; -fx-border-color: #E0E0E0; -fx-padding: 10;");
        searchBar.setMaxWidth(400);

        searchBar.textProperty().addListener((observable, oldValue, newValue) -> {
            filterEmployees(newValue);
        });

        ScrollPane sp = new ScrollPane();
        sp.setFitToWidth(true);
        sp.setStyle("-fx-background-color: transparent; -fx-padding: 10;");
        sp.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-border-radius: 10; -fx-background-radius: 10;");
        sp.setEffect(new DropShadow(10, Color.GRAY));
        sp.setMaxWidth(900);
        sp.setPrefHeight(550);

        gp = new GridPane();
        gp.setAlignment(Pos.TOP_CENTER);
        gp.setHgap(20);
        gp.setVgap(20);
        gp.setPadding(new Insets(10));

        controller = new ViewAllEmployeesController(adminId);
        employees = controller.fetchEmployeesInDepartment();

        updateGrid(employees);

        sp.setContent(gp);

        main.getChildren().addAll(title, searchBar, sp);

        getChildren().add(main);
        StackPane.setAlignment(main, Pos.CENTER);
    }

    private void updateGrid(List<User> employees) {
        gp.getChildren().clear();
        int r = 0, c = 0;
        for (User employee : employees) {
            VBox vbcard = createEmployeeCard(employee);
            gp.add(vbcard, c, r);
            c++;
            if (c > 2) { // 3 cards in each row
                c = 0;
                r++;
            }
        }
    }

    private void filterEmployees(String s) {
        if (s == null || s.isEmpty()) {
            updateGrid(employees);
        } else {
            List<User> filteredEmployees = new ArrayList<>();
            String lowerCaseQuery = s.toLowerCase();

            for (User employee : employees) {
                if (employee.getFirstName().toLowerCase().contains(lowerCaseQuery) ||
                        employee.getLastName().toLowerCase().contains(lowerCaseQuery)) {
                    filteredEmployees.add(employee);
                }
            }

            updateGrid(filteredEmployees);
        }
    }

    private VBox createEmployeeCard(User employee) {
        VBox card = new VBox(10);
        card.setAlignment(Pos.CENTER);
        card.setPadding(new Insets(20));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #E0E0E0; -fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.1), 10, 0, 0, 0);");

        Label nameLabel = new Label(employee.getFirstName() + " " + employee.getLastName());
        nameLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        nameLabel.setTextFill(Color.web("#333333"));

        Label emailLabel = new Label("Email: " + employee.getEmail());
        emailLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        emailLabel.setTextFill(Color.web("#666666"));

        Label phoneLabel = new Label("Phone: " + employee.getPhone());
        phoneLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        phoneLabel.setTextFill(Color.web("#666666"));

        Label roleLabel = new Label("Role: " + employee.getRole());
        roleLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        roleLabel.setTextFill(Color.web("#666666"));

        Label shiftLabel = new Label("Shift: " + employee.getShiftType());
        shiftLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        shiftLabel.setTextFill(Color.web("#666666"));

        Button editButton = new Button("Edit");
        editButton.setStyle("-fx-background-color: #806885; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20; -fx-background-radius: 5;");
        editButton.setOnAction(e -> openEditDialog(employee, controller));

        card.getChildren().addAll(nameLabel, emailLabel, phoneLabel, roleLabel, shiftLabel, editButton);

        return card;
    }

    private void openEditDialog(User employee, ViewAllEmployeesController controller) {

        //reference: https://examples.javacodegeeks.com/java-development/desktop-java/javafx/dialog-javafx/javafx-dialog-example/

        Dialog<User> d = new Dialog<>();
        d.setTitle("Edit Employee");
        d.setHeaderText("Edit Employee Details");

        ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
        ButtonType deleteButtonType = new ButtonType("Delete", ButtonBar.ButtonData.OTHER);
        d.getDialogPane().getButtonTypes().addAll(saveButtonType, deleteButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 10, 10, 10));

        TextField firstNameField = new TextField(employee.getFirstName());
        TextField lastNameField = new TextField(employee.getLastName());
        TextField emailField = new TextField(employee.getEmail());
        TextField phoneField = new TextField(employee.getPhone());
        TextField shiftField = new TextField(employee.getShiftType());

        grid.add(new Label("First Name:"), 0, 0);
        grid.add(firstNameField, 1, 0);
        grid.add(new Label("Last Name:"), 0, 1);
        grid.add(lastNameField, 1, 1);
        grid.add(new Label("Email:"), 0, 2);
        grid.add(emailField, 1, 2);
        grid.add(new Label("Phone:"), 0, 3);
        grid.add(phoneField, 1, 3);
        grid.add(new Label("Shift:"), 0, 4);
        grid.add(shiftField, 1, 4);

        d.getDialogPane().setContent(grid);

        d.setResultConverter(dialogButton -> {
            if (dialogButton == saveButtonType) {
                employee.setFirstName(firstNameField.getText());
                employee.setLastName(lastNameField.getText());
                employee.setEmail(emailField.getText());
                employee.setPhone(phoneField.getText());
                employee.setShiftType(shiftField.getText());
                return employee;
            } else if (dialogButton == deleteButtonType) {
                User temp = new User();
                temp.setUserId(-1);
                return temp;
            }
            return null;
        });

        d.showAndWait().ifPresent(result -> {
            if (result.getUserId() == -1) {
                boolean isDeleted = controller.deleteEmployee(employee.getUserId());
                if (isDeleted) {
                    showAlert("Success", "Employee deleted successfully!");
                    refreshEmployeeList();
                } else {
                    showAlert("Error", "Failed to delete employee.");
                }
            } else {
                boolean isSuccess = controller.updateEmployee(result);
                if (isSuccess) {
                    showAlert("Success", "Employee updated successfully!");
                    refreshEmployeeList();
                } else {
                    showAlert("Error", "Failed to update employee.");
                }
            }
        });
    }

    private void refreshEmployeeList() {
        employees = controller.fetchEmployeesInDepartment();
        updateGrid(employees);
    }
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
