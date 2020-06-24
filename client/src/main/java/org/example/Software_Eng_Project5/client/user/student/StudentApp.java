package org.example.Software_Eng_Project5.client.user.student;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.example.Software_Eng_Project5.client.user.teacher.GUI.MainTeacherController;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentApp extends UserApp
{
    private MainTeacherController mainStudentController;

    public StudentApp(Stage stage, String userName, List<Profession> professionList,
                      org.example.Software_Eng_Project5.client.SimpleClient client) throws IOException
    {
        this.stage = stage;
        this.userName = userName;
        this.client = client;
        EventBus.getDefault().register(this);


        scene = new Scene(loadFXML("studentWindow", this));
        stage.setTitle("HSTS");
        Image appIcon = new Image("\\org\\example\\Software_Eng_Project5\\client\\user\\icons\\app_icon.png");
        stage.setScene(scene);
        stage.show();

        EventBus.getDefault().register(mainStudentController);
        mainStudentController.setProfessionList(professionList);
        mainStudentController.setUserName(this.userName);
        mainStudentController.showProfessions();
    }

    @Override
    protected void setRoot(String fxml) throws IOException
    {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    @Override
    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentApp.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        if (mainStudentController == null)
            mainStudentController = fxmlLoader.getController();
        return parent;
    }

    private void bring(Message message) throws IOException
    {
        message.setCommand("Bring");
        client.sendToServer(message);
    }

    private void checkCredentials(String code, String id)
    {
        Message msg = new Message();
        msg.setSingleObject(code);
        msg.setSingleObject2(id);
        msg.setIndexString(this.userName);
        msg.setCommand("Validate");
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void createSolvedExam(PulledExam pulledExam, List<Integer> studentAnswers, int time)
    {
        Message msg = new Message();
        int grade;
        List<Boolean> checkedAnswers = checkExam(pulledExam, studentAnswers);
        grade = gradeExam(checkedAnswers, pulledExam.getOriginalExam().getGrades());
        msg.setSingleObject(pulledExam);
        msg.setObjList(studentAnswers);
        msg.setSingleObject2(this.userName);
        msg.setGrade(grade);
        msg.setTestTime(time);
        msg.setCommand("Insert");
        msg.setClassType(SolvedExam.class);
        try
        {
            client.sendToServer(msg);
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private int gradeExam(List<Boolean> checkedAnswers, List<Grade> grades)
    {
        int grade = 0;
        for (int i = 0; i < checkedAnswers.size(); i++)
        {
            if(checkedAnswers.get(i))
                grade += grades.get(i).getGrade();
        }
        return grade;
    }

    private List<Boolean> checkExam(PulledExam pulledExam, List<Integer> studentAnswers)
    {
        List<Boolean> checkedAnswers = new ArrayList<>();
        List<Question> questions = pulledExam.getOriginalExam().getQuestionList();
        for(int i = 0; i < studentAnswers.size(); i++)
        {
            checkedAnswers.add(questions.get(i).getCorrectAnsNum() == studentAnswers.get(i));
        }
        return checkedAnswers;
    }

    @SuppressWarnings("unchecked")
    public void inStudentEvent(StudentEvent event)
    {
        Message message = event.getMessage();
        String eventType = event.getEventType();

        switch (eventType)
        {
            case "Bring":
                try {
                    bring(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
            case "Create Solved Exam":
                createSolvedExam((PulledExam)message.getSingleObject(), (List<Integer>)message.getObjList(),
                       message.getTestTime());
                break;
            case "Check Credentials":
                checkCredentials((String)message.getSingleObject(), (String)message.getSingleObject2());

        }
    }
}
