module com.example.lab {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.xerial.sqlitejdbc;

    opens com.example.lab4 to javafx.fxml;
    exports com.example.lab4;
    exports com.example.lab4.Controller;
    exports com.example.lab4.Service;
    exports com.example.lab4.Domain;
    exports com.example.lab4.Repository;
    exports com.example.lab4.UI;
    opens com.example.lab4.Controller to javafx.fxml;
}