package com.example.fileiokelas.controller;

import com.example.fileiokelas.model.Model;
import com.google.gson.Gson;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Controller {
    public ListView<Model> listView;
    public TextField txtUserName;
    public TextArea textArea;

    ObservableList<Model> models;

    public void addComment(ActionEvent event) {
        Model m = new Model(txtUserName.getText(), textArea.getText());
        listView.getItems().add(m);
    }

    public void SaveCommentIO(ActionEvent event) {
        BufferedWriter writer;
        String filename = "data/comment.txt";
        try {
            writer = new BufferedWriter(new FileWriter(filename));
            ArrayList<String> stringArrayList = new ArrayList<>();
            Gson gson = new Gson();
            String jsonString = gson.toJson(listView.getItems());
            writer.write(jsonString);
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void LoadCommentIO(ActionEvent event) {
        BufferedReader reader;
        String filename = "data/comment.txt";
        try {
            reader = new BufferedReader(new FileReader(filename));
            String temp = "";
            String hasil = "";
            while ((temp = reader.readLine()) != null) {
                hasil += temp;
            }
            Gson gson = new Gson();
            Model[] modelsbaru = gson.fromJson(hasil, Model[].class);
            models = FXCollections.observableArrayList(modelsbaru);
            listView.setItems(models);
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void SaveCommentNIO(ActionEvent event) {
        Path path = Paths.get("data/comment.txt");
        try {
            Gson gson = new Gson();
            String jsonGson = gson.toJson(listView.getItems());
            Files.write(path, jsonGson.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void LoadCommentNIO(ActionEvent event) {
        Path path = Paths.get("data/comment.txt");
        String hasil = null;
        try {
            hasil = Files.readString(path);
            Gson gson = new Gson();
            Model[] modelsbaru = gson.fromJson(hasil, Model[].class);
            models = FXCollections.observableArrayList(modelsbaru);
            listView.getItems().addAll(models);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ;

    }

//    MENAMBAHKAN FITUR OPEN WITH FILE CHOOSER
    public void onOpen(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Ambil file");
        File file = fileChooser.showOpenDialog(textArea.getScene().getWindow());

        if (file == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("NO FILE SELECTED");
            alert.showAndWait();
        } else {
            Path path = Paths.get(file.toURI());
            String hasil = "";
            try {
                hasil = Files.readString(path);
                Gson gson = new Gson();
                Model[] modelbaru = gson.fromJson(hasil, Model[].class);
                models = FXCollections.observableArrayList(modelbaru);
                listView.getItems().addAll(models);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    public void onSaveAs(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        chooser.getExtensionFilters().add(extFilter);
        File fileSaveas = chooser.showSaveDialog(textArea.getScene().getWindow());
        if (fileSaveas == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("No Name and Directory Selected, FAILED to SAVE DATA");
            alert.showAndWait();
        } else {
            Path path = Paths.get(fileSaveas.toURI());
            try {
                Gson gson = new Gson();
                String jsonGson = gson.toJson(listView.getItems());
                Files.write(path, jsonGson.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}