module es.cifpvirgen.datmaker {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens es.cifpvirgen.datmaker to javafx.fxml;
    exports es.cifpvirgen.datmaker;
}