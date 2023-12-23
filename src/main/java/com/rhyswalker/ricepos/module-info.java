module com.rhyswalker.ricepos {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.base;
    requires javafx.graphics;
    requires org.json;

    opens com.rhyswalker.ricepos to javafx.fxml;

    exports com.rhyswalker.ricepos;
}