package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class mainController {

    @FXML
    private Button addCourseB;

    @FXML
    private Button addProfessionB;

    @FXML
    void addNewCourse(ActionEvent event) {
        App.setProfessionList();
        try {
            App.setRoot("addCourseWindow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void addProfession(ActionEvent event) {
        try {
            App.setRoot("addProfessionWindow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
