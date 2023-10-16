module com.mycompany.healthlinkhospital {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.sql;
    requires org.apache.pdfbox;

    opens com.mycompany.healthlinkhospital to javafx.fxml;
    opens model to javafx.base;
    exports com.mycompany.healthlinkhospital;
}
