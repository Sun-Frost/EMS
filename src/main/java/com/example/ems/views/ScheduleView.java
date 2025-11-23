package com.example.ems.views;

import com.example.ems.controllers.ScheduleController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

public class ScheduleView extends StackPane {
    public ScheduleView(int userId) {
        VBox vsb = new VBox(20);
        vsb.setAlignment(Pos.CENTER);
        vsb.setPadding(new Insets(20));
        vsb.setStyle("-fx-background-color: #D9D9D9;");

        Text title = new Text("Upcoming Week Schedule");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 28));
        title.setFill(Color.web("#806885"));

        ScheduleController controller = new ScheduleController(userId);


        Map<LocalDate, String> scheduleMap = controller.getUpcomingWeekSchedule();

        TableView<Map.Entry<String, String>> scheduleTable = createScheduleTable(scheduleMap);
        scheduleTable.setStyle("-fx-background-color: #f4f4f4; -fx-border-color: #cccccc; -fx-font-size: 18px;-fx-border-radius: 10; -fx-background-radius: 10;");
        scheduleTable.setEffect(new DropShadow(10, Color.GRAY));
        vsb.getChildren().addAll(title, scheduleTable);

        getChildren().add(vsb);
        StackPane.setAlignment(vsb, Pos.CENTER);
    }

    private TableView<Map.Entry<String, String>> createScheduleTable(Map<LocalDate, String> scheduleMap) {
        TableView<Map.Entry<String, String>> table = new TableView<>();
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Auto-resize columns
        table.setStyle("-fx-background-color: white; -fx-border-color: grey; -fx-border-radius: 10; -fx-background-radius: 10;");

        TableColumn<Map.Entry<String, String>, String> dateColumn = new TableColumn<>("Date");
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        dateColumn.setStyle("-fx-alignment: CENTER;");

        TableColumn<Map.Entry<String, String>, String> shiftColumn = new TableColumn<>("Shift");
        shiftColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        shiftColumn.setStyle("-fx-alignment: CENTER;");

        table.getColumns().add(dateColumn);
        table.getColumns().add(shiftColumn);

        ObservableList<Map.Entry<String, String>> data = FXCollections.observableArrayList();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (Map.Entry<LocalDate, String> entry : scheduleMap.entrySet()) {
            String formattedDate = entry.getKey().format(formatter);
            data.add(new java.util.AbstractMap.SimpleEntry<>(formattedDate, entry.getValue()));
        }
        table.setItems(data);

        adjustTableSize(table);

        return table;
    }

    private void adjustTableSize(TableView<Map.Entry<String, String>> table) {
        double h = table.getItems().size() * 40 + 40;
        table.setPrefHeight(h);

        double w = table.getColumns().size() * 170;
        table.setPrefWidth(w);

        table.setMaxHeight(h);
        table.setMaxWidth(w);
    }
}