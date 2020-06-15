package org.example.Software_Eng_Project5.client.user.teacher;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class TeacherGUI {

    @FXML
    private Label userNameLabel;

    public void setUserName(String userName){
        userNameLabel.setText(userName);
    }

}