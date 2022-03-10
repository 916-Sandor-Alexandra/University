module com.example.interpretergui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.interpretergui to javafx.fxml;
    exports com.example.interpretergui;
}