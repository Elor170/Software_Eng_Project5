package org.example.Software_Eng_Project5.client.user.headmaster;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserApp;
import org.example.Software_Eng_Project5.client.user.headmaster.GUI.mainHeadmasterController;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.Student;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

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


    private void ChangeTime(Message message) throws IOException
    {
        message.setCommand("Confirm Time");
        client.sendToServer(message);
    }

    private void DeleteTime(Message message) throws IOException
    {
        message.setCommand("Delete Time");
        client.sendToServer(message);
    }

    @Subscribe
    public void inHeadmasterEvent(HeadmasterEvent event)
    {
        Message message = event.getMessage();
        if(event.getEventType().equals("Change Time"))
        {
            try
            {
                ChangeTime(message);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else if(event.getEventType().equals("Delete Time"))
        {
            try
            {
                DeleteTime(message);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        else
        {
            try
            {
                bring(message);
            } catch (IOException e)
            {
                e.printStackTrace();
            }
        }

    }
}
