module com.astefasi.interfazastefasi {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens com.astefasi.interfazastefasi to javafx.fxml;
    exports com.astefasi.interfazastefasi;
}