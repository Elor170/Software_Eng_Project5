package org.example.Software_Eng_Project5.client.user.headmaster;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class HeadmasterApp extends UserApp
{
    private org.example.Software_Eng_Project5.client.user.headmaster.GUI.mainHeadmasterController mainHeadmasterController;

    public HeadmasterApp(Stage stage, String username, org.example.Software_Eng_Project5.client.SimpleClient client) throws IOException
    {
        this.stage = stage;
        this.userName = username;
        this.client = client;
        EventBus.getDefault().register(this);

        scene = new Scene(loadFXML("headmasterWindow", this));
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.setScene(scene);
        stage.show();

        EventBus.getDefault().register(mainHeadmasterController);
        mainHeadmasterController.setUserName(this.userName);
    }

    @Override
    protected void setRoot(String fxml) throws IOException
    {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    @Override
    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(HeadmasterApp.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        if (mainHeadmasterController == null)
            mainHeadmasterController = fxmlLoader.getController();
        return parent;
    }

    private void bring(Message message) throws IOException
    {
        message.setCommand("Bring");
        client.sendToServer(message);
    }

    @Subscribe
    public void inHeadmasterEvent(HeadmasterEvent event)
    {
        Message message = event.getMessage();

        try {
            bring(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
