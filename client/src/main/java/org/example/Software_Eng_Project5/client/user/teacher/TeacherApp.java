package org.example.Software_Eng_Project5.client.user.teacher;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.example.Software_Eng_Project5.client.user.teacher.GUI.MainTeacherController;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class TeacherApp extends UserApp
{

    private MainTeacherController mainTeacherController;

    public TeacherApp(Stage stage, String userName, List<Profession> professionList,
                      org.example.Software_Eng_Project5.client.SimpleClient client) throws IOException
    {
        this.stage = stage;
        this.userName = userName;
        this.client = client;
        EventBus.getDefault().register(this);


        scene = new Scene(loadFXML("teacherWindow", this));
        stage.setTitle("HSTS");
        Image appIcon = new Image("\\org\\example\\Software_Eng_Project5\\client\\user\\icons\\app_icon.png");
        stage.setScene(scene);
        stage.show();

        EventBus.getDefault().register(mainTeacherController);
        mainTeacherController.setProfessionList(professionList);
        mainTeacherController.setUserName(this.userName);
        mainTeacherController.showProfessions();
    }

    @Override
    protected void setRoot(String fxml) throws IOException
    {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    @Override
    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        if (mainTeacherController == null)
            mainTeacherController = fxmlLoader.getController();
        return parent;
    }


    private void bring(Message message) throws IOException
    {
        message.setCommand("Bring");
        client.sendToServer(message);
    }

    @SuppressWarnings("unchecked")
    private void createQuestion(Message msg)
    {
        msg.setClassType(Question.class);
        msg.setCommand("Insert");
        ((List<String>) msg.getObjList()).add(userName);
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void editQuestion(Question question, List<String> textAndNewAnswers, int newCorrectAnswerNum)
    {
        Message msg = new Message();
        List<Answer> answers = question.getAnswers();
        question.setQuestionText(textAndNewAnswers.get(0));
        question.setCorrectAnsNum(newCorrectAnswerNum);
        for(int i = 0; i < answers.size(); i++)
        {
            answers.get(i).setAnsText(textAndNewAnswers.get(i + 1));
        }
        msg.setCommand("Update");
        msg.setClassType(Question.class);
        msg.setSingleObject(question);
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() throws Exception {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(mainTeacherController);
        super.stop();
    }

    @SuppressWarnings("unchecked")
    private void createExam(Message msg)
    {
        msg.setClassType(Exam.class);
        msg.setCommand("Insert");
        ((List<String>) msg.getObjList2()).add(userName);
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void editExam(Exam exam, List<Question> questions, int testTime, List<String> textList, List<Integer> grades)
    {
        Message msg = new Message();
        exam.setQuestionList(questions);
        exam.setTextForStudent(textList.get(0));
        exam.setTextForTeacher(textList.get(1));
        exam.setTestTime(testTime);
        msg.setCommand("Update");
        msg.setClassType(Exam.class);
        msg.setSingleObject(exam);
        msg.setObjList3(grades);
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void pullExam(Exam exam, String executionCode)
    {
        Message msg = new Message();
        msg.setClassType(PulledExam.class);
        msg.setCommand("Insert");
        msg.setSingleObject(exam);
        msg.setSingleObject2(executionCode);
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inTeacherEvent(TeacherEvent event){
        Message message = event.getMessage();
        String eventType = event.getEventType();

        switch (eventType){
            case "Bring":
                try {
                    bring(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;

            case "Create Question":
                createQuestion(message);
                break;

            case "Update Question":
                editQuestion((Question)message.getSingleObject(), (List<String>)message.getObjList(),
                        message.getIndexInt());
                break;

            case "Create Exam":
                createExam(message);
                break;

            case "Update Exam":
                editExam((Exam)message.getSingleObject(), (List<Question>)message.getObjList(), message.getTestTime(),
                        (List<String>)message.getObjList2(), (List<Integer>)message.getObjList3());
                break;

            case "Pull Exam":
                    pullExam((Exam)message.getSingleObject(), (String)message.getSingleObject2());
                break;
        }
    }
}
