package org.example.Software_Eng_Project5.client.user.student.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class mainStudentController {

    private String userName;
    private PulledExam pulledExam;
    private List<SolvedExam> solvedExams;
    private StudentExamController examController = new StudentExamController();
    private studentSolvedExamController solvedExamController = new studentSolvedExamController();
    private studentGradesWindowController gradesWindowController = new studentGradesWindowController();

    @FXML
    private TextField executionCodeTF;

    @FXML
    private Label userNameL;

    @FXML
    private TextField idTF;

    @FXML
    private Label errorLabel;

    @FXML
    void executeExam(ActionEvent event) {
        errorLabel.setVisible(false);
        String execCode = executionCodeTF.getText();
        String id = idTF.getText();
        Message message = new Message();
        message.setSingleObject(execCode);
        message.setSingleObject2(id);
        message.setIndexString(this.userName);

        EventBus.getDefault().post(new StudentEvent(message, "Check Credentials"));
    }

    @FXML
    void seeExams(ActionEvent event) throws IOException
    {
        Message message = new Message();
        message.setSingleObject(userName);

        EventBus.getDefault().post(new StudentEvent(message, "Get Solved Exams"));
    }

    public void setUserName(String userName){
        this.userName = userName;
        userNameL.setText(this.userName);
        errorLabel.setVisible(false);
    }

    @SuppressWarnings("unchecked")
    @Subscribe
    public void inStudentEvent(StudentEvent event) throws IOException
    {
        Message message = event.getMessage();
        String eventType = event.getEventType();

        if(eventType.equals("Start Exam")){
            if(message.getSingleObject() != null)
            {
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
            else
            {
                errorLabel.setVisible(true);
            }
        }
        else if(eventType.equals("Solved Exam"))
        {
            solvedExams = (List<SolvedExam>)message.getObjList();
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
        else if(eventType.equals("Solved Exams"))
        {
            Platform.runLater(()->{
                try
                {
                    gradesWindowController.seeExams((List<SolvedExam>) message.getObjList());
                } catch (IOException e)
                {
                    e.printStackTrace();
                }
            });
        }
    }
}

