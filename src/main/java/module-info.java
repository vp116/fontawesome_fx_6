module com.cardosama.fontawesome_fx_6 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    requires java.json;

    opens com.cardosama.fontawesome_fx_6 to javafx.fxml;
    exports com.cardosama.fontawesome_fx_6;
}