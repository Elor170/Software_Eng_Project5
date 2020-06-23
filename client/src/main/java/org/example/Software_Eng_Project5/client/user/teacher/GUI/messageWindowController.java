package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class messageWindowController {

    @FXML
    private Label messageLabel;

    public void setMessage(String text){
        messageLabel.setText(text);
    }

}
