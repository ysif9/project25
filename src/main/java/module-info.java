module com.project25 {
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    requires javafx.fxml;
    requires javafx.controls;
    requires de.jensd.fx.glyphs.fontawesome;
    requires atlantafx.base;


    opens com.project25 to javafx.fxml;
    exports com.project25;
    exports com.project25.Models;
    exports com.project25.Exceptions;
    exports com.project25.Controllers;
    exports com.project25.Components;



}