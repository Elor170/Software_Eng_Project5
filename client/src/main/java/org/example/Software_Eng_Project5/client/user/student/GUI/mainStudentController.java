package org.example.Software_Eng_Project5.client.user.student.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class mainStudentController {

    private String userName;
    private PulledExam pulledExam;
    private StudentExamController examController = new StudentExamController();
    private studentSolvedExamController solvedExamController = new studentSolvedExamController();

    @FXML
    private TextField executionCodeTF;

    @FXML
    private Label userNameL;

    @FXML
    private TextField idTF;

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

    @Subscribe
    public void inStudentEvent(StudentEvent event)
    {
        Message message = event.getMessage();
        String eventType = event.getEventType();

        if(eventType.equals("Start Exam")){
            Platform.runLater(()->{
                this.pulledExam = (PulledExam) message.getSingleObject();
                System.out.println(pulledExam.getOriginalExam().getCode());
                try
                {
                    examController.startExam(pulledExam);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        }
        else if(eventType.equals("Solved Exam"))
        {
            Platform.runLater(()->{
                try
                {
                    solvedExamController.showGradeWindow(message);
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        }
    }
}

