package org.example.Software_Eng_Project5.client.user.teacher;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class ExamWindowController {

    @FXML
    private VBox coursesVBox;

    @FXML
    private Label examTitle;

    @FXML
    private VBox contentVBox;

    @FXML
    private TextField examTimeTF;

    @FXML
    private TextField examTypeTF;

    @FXML
    private TextArea teacherCommentsTA;

    @FXML
    private TextArea studentCommentsTA;

    @FXML
    private HBox buttonsHBox;

    @FXML
    private Button pullExamB;

    @FXML
    void pullExam(ActionEvent event) {

    }

}
