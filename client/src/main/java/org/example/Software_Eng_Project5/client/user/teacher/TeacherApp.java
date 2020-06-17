package org.example.Software_Eng_Project5.client.user.teacher;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.example.Software_Eng_Project5.client.user.teacher.GUI.MainTeacherController;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;


/**
 * JavaFX App
 */
public class TeacherApp extends UserApp {

    private static MainTeacherController mainTeacherController;

    public TeacherApp(Stage stage, String userName) throws IOException {
        this.stage = stage;
        EventBus.getDefault().register(this);
        //scene.setRoot(loadFXML("teacherWindow", this));
        scene = new Scene(loadFXML("teacherWindow", this));
        stage.setScene(scene);
        stage.show();
        this.userName = userName;
        mainTeacherController.setUserName(this.userName);
    }

    @Override
    protected void setRoot(String fxml) throws IOException {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    @Override
    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        TeacherApp.mainTeacherController = fxmlLoader.getController();
        return parent;
    }

//    private void createQuestion(String newQuestion, String ans1, String ans2, String ans3, String ans4, int correctAns)
//    {
//        Object question = new Question(newQuestion, ans1, ans2, ans3, ans4, correctAns);
//        Message message = new Message();
//        message.setCommand("Insert Question");
//        message.setSingleObject(question);
//        try {
//            client.sendToServer(message);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

//    private void bringQuestionsList(List<Integer> questionsNumbers) throws IOException
//    {
//        Message message = new Message("Get Questions List", questionsNumbers);
//        client.sendToServer(message);
//    }
//
//    private Exam createExam(String type, String teacher, int time, List<String> notes, int code) throws IOException
//    {
//        Object exam = new Exam(questions, type, teacher, time, notes, code);
//        Message message;
//        message = new Message("Insert Exam", exam);
//        client.sendToServer(message);
//        return (Exam)exam;
//    }
//
//    private Exam retrieveExam(int examCode) throws IOException
//    {
//        Message msg = new Message("Retrieve Exam", examCode);
//        client.sendToServer(msg);
//        Exam exam = (Exam)message.getObjList();
//        return exam;
//    }
//
//    private boolean checkPermissions(String userName, String professionToCheck) throws IOException
//    {
//        Message msg = new Message("GetObject", null);
//        client.sendToServer(msg);
//        List<String> professions = ((Teacher)message.getObjList()).getProfessions();
//        for (String profession : professions)
//        {
//            if(profession.equals(professionToCheck))
//                return true;
//        }
//        return false;
//    }
//
//    private void confirmGrade()
//    {
//
//    }
//
//    private void requestExamTimeChange(Exam exam, int time)
//    {
//
//    }
//
//    @Subscribe
//    public void onUserEvent(UserEvent userEvent)
//    {
//        message = userEvent.getMessage();
//    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inTeacherEvent(TeacherEvent event){
        if (event.getEventType().equals("Create Question")){
            Message message = event.getMessage();
            int correctAns = message.getIndexInt();
            List<String> textList = (List<String>) message.getObjList();
            String newQuestion = textList.get(0);
            String ans1 = textList.get(1);
            String ans2 = textList.get(2);
            String ans3 = textList.get(3);
            String ans4 = textList.get(4);
            //createQuestion(newQuestion, ans1, ans2, ans3, ans4, correctAns);
        }
    }
}