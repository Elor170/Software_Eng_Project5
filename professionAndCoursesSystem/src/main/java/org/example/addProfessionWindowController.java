package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import org.example.Software_Eng_Project5.entities.Profession;

import java.io.IOException;


public class addProfessionWindowController {

    @FXML
    private HBox hbox;

    @FXML
    private TextField professionNameTF;

    @FXML
    private TextField professionCodeTF;

    @FXML
    private Button addB;

    @FXML
    private Button returnB;

    @FXML
    void return2Main(ActionEvent event) {
        try {
            App.setRoot("mainWindow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void add2DB(ActionEvent event) {
        String code = professionCodeTF.getText();
        String name = professionNameTF.getText();
        Profession profession = new Profession(code, name);
        App.addObject2DB(profession);
    }

}
