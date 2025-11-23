package com.example.ems.views;

import com.example.ems.controllers.SceneController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class SidebarView extends VBox {
    private final VBox menu;
    private boolean isExpanded = false;
    private final Stage stage;
    private final String role;

    public SidebarView(Stage stage, int userId, String role) {
        this.stage = stage;
        this.role = role;

        setPadding(new Insets(10));
        setSpacing(10);
        setStyle("-fx-background-color: #F0F0F0;");
        setPrefWidth(60); // Default width (collapsed)

        menu = new VBox(10);
        menu.setPadding(new Insets(10));
        menu.setStyle("-fx-background-color: #F0F0F0;");

        String[] menuItems;
        String[] icons;

        if (role.equals("Admin")) {
            menuItems = new String[]{"Dashboard", "Employees", "Leave Requests", "Profile", "Logout"};
            icons = new String[]{
                    "file:src/main/resources/home.png",
                    "file:src/main/resources/employees.png",
                    "file:src/main/resources/leave.png",
                    "file:src/main/resources/profile.png",
                    "file:src/main/resources/logout.png"
            };
        } else if (role.equals("Employee")) {
            menuItems = new String[]{"Dashboard", "Schedule", "Leave Request", "Profile", "Logout"};
            icons = new String[]{
                    "file:src/main/resources/home.png",
                    "file:src/main/resources/schedule.png",
                    "file:src/main/resources/leave.png",
                    "file:src/main/resources/profile.png",
                    "file:src/main/resources/logout.png"
            };
        } else {
            menuItems = new String[]{"Dashboard", "Profile", "Logout"};
            icons = new String[]{
                    "file:src/main/resources/home.png",
                    "file:src/main/resources/profile.png",
                    "file:src/main/resources/logout.png"
            };
        }

        for (int i = 0; i < menuItems.length; i++) {
            ImageView icon = new ImageView(new Image(icons[i]));
            icon.setFitWidth(25);
            icon.setFitHeight(25);

            Label label = new Label(menuItems[i]);
            label.setFont(Font.font("Arial", FontWeight.BOLD, 14));
            label.setVisible(false);

            HBox mItem = new HBox(10, icon, label);
            mItem.setAlignment(Pos.CENTER_LEFT);
            mItem.setPadding(new Insets(5));

            menu.getChildren().add(mItem);

            final String menuName = menuItems[i];

            mItem.addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                if (menuName.equals("Logout")) {
                    Scene loginScene = LoginView.createLoginScene(stage);
                    stage.setScene(loginScene);
                } else {
                    SceneController.switchScene(stage, menuName, userId, role);
                }
            });
        }

        getChildren().add(menu);
        setOnMouseEntered(e -> expandSidebar());
        setOnMouseExited(e -> collapseSidebar());
    }

    private void expandSidebar() {
        if (isExpanded) return;
        isExpanded = true;

        for (var node : menu.getChildren()) {
            if (node instanceof HBox menuItem) {
                Label label = (Label) menuItem.getChildren().get(1);
                label.setVisible(true);
            }
        }
        setPrefWidth(200);
    }

    private void collapseSidebar() {
        if (!isExpanded) return;
        isExpanded = false;

        for (var node : menu.getChildren()) {
            if (node instanceof HBox menuItem) {
                Label label = (Label) menuItem.getChildren().get(1);
                label.setVisible(false);
            }
        }
        setPrefWidth(55);
    }
}