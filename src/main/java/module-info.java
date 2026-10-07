module fintrack {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    opens fintrack.controller to javafx.fxml;
    opens fintrack.app to javafx.fxml;
    opens fintrack.model to javafx.base;
    exports fintrack.app;
}