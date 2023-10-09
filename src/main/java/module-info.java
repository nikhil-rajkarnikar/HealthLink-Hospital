module com.mycompany.healthlinkhospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.healthlinkhospital to javafx.fxml;
    opens model to javafx.base;
    exports com.mycompany.healthlinkhospital;
}
