module com.example.demo3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.demo3 to javafx.fxml;
    exports com.example.demo3;
    exports db2;
    opens db2 to javafx.fxml;
}