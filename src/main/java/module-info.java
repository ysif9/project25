module com.project25 {
    requires javafx.controls;
    requires javafx.fxml;
    requires de.jensd.fx.glyphs.fontawesome;
    requires java.sql;
    requires org.xerial.sqlitejdbc;
    requires java.desktop;
    requires jdk.xml.dom;
    requires atlantafx.base;
    requires org.jetbrains.annotations;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.ikonli.feather;
    requires org.kordamp.ikonli.material2;
    requires org.kordamp.ikonli.core;
    requires javafx.swing;

    opens com.project25 to javafx.fxml;
    exports com.project25;
    exports com.project25.Controllers;
    exports com.project25.Models;
    exports com.project25.Views;
    exports com.project25.Components;
    exports com.project25.Exceptions;



}