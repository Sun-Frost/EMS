module com.example.ems {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires java.desktop;
    requires java.sql;

    opens com.example.ems.models to javafx.base;
    exports com.example.ems;
    opens com.example.ems to javafx.fxml;
    exports com.example.ems.models;
}