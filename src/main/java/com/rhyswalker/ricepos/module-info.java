module com.rhyswalker.ricepos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.base;

    opens com.rhyswalker.ricepos to javafx.fxml;

    exports com.rhyswalker.ricepos;
}