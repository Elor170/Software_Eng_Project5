package org.example.Software_Eng_Project5.client.user.teacher;


import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;


/**
 * JavaFX App
 */
public class TeacherApp extends UserApp {

    private static TeacherGUI teacherGUI;

    public TeacherApp(String userName) throws IOException {
        EventBus.getDefault().register(this);
        scene.setRoot(loadFXML("teacherWindow", this));

        this.userName = userName;
        teacherGUI.setUserName(this.userName);
    }

    @Override
    protected void setRoot(String fxml) throws IOException {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    @Override
    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        TeacherApp.teacherGUI = fxmlLoader.getController();
        return parent;
    }




}