package org.example.Software_Eng_Project5.client.user;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.Teacher;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

/**
 * JavaFX App
 */
public class UserApp extends Application {

    protected Stage stage;
    protected static Scene scene;
    protected org.example.Software_Eng_Project5.client.SimpleClient client;
    protected String userName;
    private String password;
    private UserGUI userGUI;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        EventBus.getDefault().register(this);
        client = org.example.Software_Eng_Project5.client.SimpleClient.getClient();
        client.openConnection();
        EventBus.getDefault().register(client);
        scene = new Scene(loadFXML("userWindow", this));
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
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().unregister(client);
        super.stop();
        System.exit(0);
    }



    @Subscribe
    @SuppressWarnings("unchecked")
    public void inUserEvent (UserEvent event) {
        Message massage = event.getMessage();
        String eventType = event.getEventType();
        if(eventType.equals("LogIn")){
            List<String> stringList = (List<String>)massage.getObjList();
            this.userName = stringList.get(0);
            this.password = stringList.get(1);
        }

        if(eventType.equals("LogIn check")){
            logIn(massage);
        }

    }

    private void logIn(Message massage){
        Platform.runLater(() -> {
        boolean isUserNamePasswordMatch = false;
        String userType = massage.getType();
        Object user = massage.getSingleObject();

        if(userType != null){
            if(userType.equals("Teacher")){
                Teacher teacher = (Teacher)user;
                if (teacher.getUserName().equals(this.userName)
                        && teacher.getPassword().equals(this.password)){
                    try {
                        TeacherApp teacherApp = new TeacherApp(this.stage ,this.userName);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    isUserNamePasswordMatch = true;
                }
            }

        }
            if (!isUserNamePasswordMatch) {
                userGUI.logInFailed();
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }

}