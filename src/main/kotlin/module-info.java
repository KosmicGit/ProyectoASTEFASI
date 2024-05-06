module com.astefasi.interfazastefasi {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.desktop;
    requires mysql.connector.j;
    requires java.sql;
    requires io.github.cdimascio.dotenv.java;
    requires org.kohsuke.github.api;


    opens com.astefasi.interfazastefasi to javafx.fxml;
    exports com.astefasi.interfazastefasi;
    exports com.astefasi.interfazastefasi.screen.carga;
    exports com.astefasi.interfazastefasi.screen.principal;
}