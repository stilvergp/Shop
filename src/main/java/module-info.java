module com.github.stilvergp {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    exports com.github.stilvergp;
    exports com.github.stilvergp.view;
    exports com.github.stilvergp.model.entity;
    opens com.github.stilvergp.view to javafx.fxml;
    exports com.github.stilvergp.controller;
    opens com.github.stilvergp.controller to javafx.fxml;
}