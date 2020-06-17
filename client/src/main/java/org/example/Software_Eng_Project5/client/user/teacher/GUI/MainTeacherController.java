package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;

import java.io.IOException;


public class MainTeacherController {

    @FXML
    private Label userNameLabel;

    @FXML
    private Button showQuestionsB;

    @FXML
    private BorderPane borderPane;

    @FXML
    private VBox questionsList;


    public void setUserName(String userName){
        userNameLabel.setText(userName);
    }

    @FXML
    private void showQuestionsB(ActionEvent event){

        VBox vb = new VBox();
        Label l = new Label("Questions");
        vb.setAlignment(Pos.TOP_CENTER);
        vb.getChildren().add(l);
        l = new Label("Q1");
        vb.getChildren().add(l);
        l = new Label("Q2");
        vb.getChildren().add(l);
        l = new Label("Q3");
        vb.getChildren().add(l);
        Button addQuestion = new Button("Create Question");
        addQuestion.setOnAction((this::onCreateQuestion));
        vb.getChildren().add(addQuestion);

        borderPane.setCenter(vb);
    }

    @FXML
    private void onCreateQuestion(ActionEvent event){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("createQuestionWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.show();
        CreateQuestionController.stage = stage;
    }

}