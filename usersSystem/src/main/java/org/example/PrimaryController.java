package org.example;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

public class PrimaryController {

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private ChoiceBox<String> userTypeChoiceBox;

    @FXML
    private Button addUserButton;

    @FXML
    void addUser(ActionEvent event) {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        String userType = userTypeChoiceBox.getValue();
        App.addUser2DB(userName, password, userType);
    }

}

