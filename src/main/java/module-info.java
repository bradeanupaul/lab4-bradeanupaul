module com.example.lab4 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.xerial.sqlitejdbc;

    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
}