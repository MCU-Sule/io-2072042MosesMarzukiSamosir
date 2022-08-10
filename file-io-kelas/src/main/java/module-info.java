module com.example.fileiokelas {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;


    opens com.example.fileiokelas to javafx.fxml;
    opens com.example.fileiokelas.model;
    exports com.example.fileiokelas;
    exports com.example.fileiokelas.controller;
    opens com.example.fileiokelas.controller to javafx.fxml;
}