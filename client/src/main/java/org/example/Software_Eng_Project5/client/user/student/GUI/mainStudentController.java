package org.example.Software_Eng_Project5.client.user.student.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class mainStudentController {

    private String userName;
    private PulledExam pulledExam;
    private Exam originalExam;
    private List<Question> questionList;

    @FXML
    private Button executeExamB;

    @FXML
    private TextField executionCodeTF;

    @FXML
    private Label userNameL;

    @FXML
    private TextField idTF;


    @FXML
    private Label examCodeTF;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button endExamB;

    @FXML
    void executeExam(ActionEvent event) {
        String execCode = executionCodeTF.getText();
        String id = idTF.getText();
        Message message = new Message();
        message.setSingleObject(execCode);
        message.setSingleObject2(id);
        message.setIndexString(this.userName);

        EventBus.getDefault().post(new StudentEvent(message, "Check Credentials"));
    }

    public void setUserName(String userName){
        this.userName = userName;
        userNameL.setText(this.userName);
    }

    @FXML
    void endExam(ActionEvent event) {

    }

    private void startExam(){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(StudentApp.class.getResource("studentExamWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
//        examCodeTF.setText(this.originalExam.getCode());
//        this.contentVbox.getChildren().clear();
//        this.contentVbox = new VBox();
//        this.originalExam = this.pulledExam.getOriginalExam();
//        this.questionList = this.originalExam.getQuestionList();
//        VBox questionVBox;
//        HBox topQuestionHBox;
//        Label codeLabel;
//        Label textLabel;
//        VBox answersVBox;
//        CheckBox answerCheckBox;
//        List<Answer> answers;
//        for (Question question: this.questionList){
//            questionVBox = new VBox();
//            codeLabel = new Label(question.getCode());
//            textLabel = new Label(question.getQuestionText());
//            topQuestionHBox = new HBox(codeLabel, textLabel);
//            questionVBox.getChildren().add(topQuestionHBox);
//            answers = question.getAnswers();
//            for (Answer answer: answers){
//                answerCheckBox = new CheckBox(answer.getAnsText());
//                questionVBox.getChildren().add(answerCheckBox);
//            }
//
//            this.contentVbox.getChildren().add(questionVBox);
//        }
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inStudentEvent(StudentEvent event)
    {
        Message message = event.getMessage();
        String eventType = event.getEventType();

        if(eventType.equals("Start Exam")){
            Platform.runLater(()->{
                this.pulledExam = (PulledExam) message.getSingleObject();
                System.out.println(pulledExam.getOriginalExam().getCode());
                this.startExam();
            });
        }

    }
}

