module com.example.splitter {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.pdfbox;

    opens com.example.splitter to javafx.fxml;
    exports com.example.splitter;
}