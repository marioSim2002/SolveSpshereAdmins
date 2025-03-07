module com.example.solvesphereadmins {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.sql;
    requires spring.security.crypto;
    requires itextpdf;

    opens com.example.solvesphereadmins to javafx.fxml;
    exports com.example.solvesphereadmins;
    exports com.example.solvesphereadmins.Controllers;
    opens com.example.solvesphereadmins.Controllers to javafx.fxml;
    opens com.example.solvesphereadmins.AdminUnit to javafx.base; // FIX
    exports com.example.solvesphereadmins.SecurityUnit;
    opens com.example.solvesphereadmins.SecurityUnit to javafx.fxml;
    exports com.example.solvesphereadmins.RetrievedUserData;
    opens com.example.solvesphereadmins.RetrievedUserData to javafx.fxml;
}