module com.mycompany.getitnow {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;

    opens com.mycompany.getitnow to javafx.fxml;
    opens model to javafx.base;
    exports com.mycompany.getitnow;
}
