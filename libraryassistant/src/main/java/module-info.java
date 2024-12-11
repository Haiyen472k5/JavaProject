module project.libraryassistant {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires de.jensd.fx.glyphs.fontawesome;
    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires okhttp3;
    requires mysql.connector.j;
    opens project.libraryassistant to javafx.fxml;
    exports project.libraryassistant;

}