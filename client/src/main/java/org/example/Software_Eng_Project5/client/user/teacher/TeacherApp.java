package org.example.Software_Eng_Project5.client.user.teacher;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.client.user.UserGUI;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.Teacher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class TeacherApp {

    private static Scene scene;
    private org.example.Software_Eng_Project5.client.SimpleClient client;


    public TeacherApp(org.example.Software_Eng_Project5.client.SimpleClient client, Scene scene) throws IOException {
        this.client = client;
        scene.setRoot(loadFXML("teacherWindow"));
    }

    private static void setRoot(String fxml) throws IOException {
        scene.setRoot(TeacherApp.loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }


}