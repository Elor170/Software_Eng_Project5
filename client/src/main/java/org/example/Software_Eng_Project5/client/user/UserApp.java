package org.example.Software_Eng_Project5.client.user;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.Profession;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class UserApp extends Application {

    protected static Stage stage;
    protected static Scene scene;
    protected org.example.Software_Eng_Project5.client.SimpleClient client;
    private UserGUI userGUI;
    protected String userName;

    @Override
    public void start(Stage stage) throws IOException {
            this.stage = stage;
            client = org.example.Software_Eng_Project5.client.SimpleClient.getClient();
            client.openConnection();

            EventBus.getDefault().register(this);
            EventBus.getDefault().register(client);

            scene = new Scene(loadFXML("userWindow", this));
            stage.setTitle("HSTS");
            Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
            stage.getIcons().add(appIcon);
            stage.setScene(scene);
            stage.show();
    }

    protected void setRoot(String fxml) throws IOException {
        scene.setRoot(this.loadFXML(fxml, this));
    }

    protected Parent loadFXML(String fxml, UserApp userApp) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserApp.class.getResource(fxml + ".fxml"));
        Parent parent = fxmlLoader.load();
        userApp.userGUI = fxmlLoader.getController();
        return parent;
    }

    @Override
    public void stop() throws Exception {
        if(this.userName != null){
            Message message = new Message();
            message.setIndexString(this.userName);
            EventBus.getDefault().post(new UserEvent(message, "LogOut"));
        }
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(client);
        super.stop();
        System.exit(0);
    }

    @Subscribe
    public void inUserEvent (UserEvent event) {
        Message massage = event.getMessage();
        String eventType = event.getEventType();

        if(eventType.equals("LogIn check")){
            logIn(massage);
        }
    }

    @SuppressWarnings("unchecked")
    private void logIn(Message massage){
        Platform.runLater(() -> {
            String userType = massage.getType();
            String userName = massage.getIndexString();

            switch (userType) {
                case "Headmaster":
                    //TODO
                    break;

                case "Teacher":
                    try {
                        this.userName = userName;
                        TeacherApp teacherApp = new TeacherApp(this.stage, userName,
                                (List<Profession>) massage.getObjList(), this.client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Student":
                    try {
                        this.userName = userName;
                        StudentApp studentApp = new StudentApp(this.stage, userName, this.client);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                case "Already connected":
                    userGUI.alreadyConnected();
                    break;

                case "No match found":
                    userGUI.logInFailed();
                    break;
            }
        });
    }

    public static void main(String[] args) { launch(); }

}