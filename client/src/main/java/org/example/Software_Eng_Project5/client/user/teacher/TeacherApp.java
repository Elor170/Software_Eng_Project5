package org.example.Software_Eng_Project5.client.user.teacher;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.example.Software_Eng_Project5.client.user.teacher.GUI.MainTeacherController;
import org.example.Software_Eng_Project5.entities.Course;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.Profession;
import org.example.Software_Eng_Project5.entities.Question;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * JavaFX App
 */
public class TeacherApp extends UserApp {

    private  MainTeacherController mainTeacherController;

    public TeacherApp(Stage stage, String userName, List<Profession> professionList,
                      org.example.Software_Eng_Project5.client.SimpleClient client) throws IOException {
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
    protected void setRoot(String fxml) throws IOException {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    @Override
    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException {
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


    private void createQuestion(Message msg)
    {
        msg.setClassType(Question.class);
        msg.setCommand("Insert");
        try {
            client.sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void createQuestion(String questionText, String ans1, String ans2, String ans3, String ans4, int correctAns,
//                                Profession profession, List<Course> courses)
//    {
//
//        Message msg = new Message();
//        Question question = new Question(questionText, ans1, ans2, ans3, ans4, correctAns, profession, courses);
//        msg.setClassType(Question.class);
//        msg.setCommand("Insert");
//        msg.setSingleObject(question);
//        try {
//            client.sendToServer(msg);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public void stop() throws Exception {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(mainTeacherController);
        super.stop();
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

            case "Update":
                //TODO
                break;

            case "Create Question":
//                List<String> textList = (List<String>) message.getObjList();
//                createQuestion(textList.get(0), textList.get(1), textList.get(2), textList.get(3), textList.get(4),
//                        message.getIndexInt(), (Profession) message.getSingleObject(), (List<Course>) message.getObjList2());
                createQuestion(message);
                break;
        }
    }
}
