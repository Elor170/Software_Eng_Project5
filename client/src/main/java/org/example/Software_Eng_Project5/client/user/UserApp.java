package org.example.Software_Eng_Project5.client.user;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
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

    private static Scene scene;
    private org.example.Software_Eng_Project5.client.SimpleClient client;

    @Override
    public void start(Stage stage) throws IOException {
        EventBus.getDefault().register(this);
        client = org.example.Software_Eng_Project5.client.SimpleClient.getClient();
        client.openConnection();
        scene = new Scene(loadFXML("userWindow"));
        stage.setScene(scene);
        stage.show();
    }

    private static void setRoot(String fxml) throws IOException {
        scene.setRoot(UserApp.loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(UserApp.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }



    @Override
    public void stop() throws Exception {
        // TODO Auto-generated method stub
        EventBus.getDefault().unregister(this);
        super.stop();
        System.exit(0);
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void logInEvent(UserEvent event) {
        Platform.runLater(() -> {
            Message message = event.getMessage();
            String password = UserGUI.getPassword();

            if(message.getType() != null){
                if (message.getType().equals("Teacher") &&
                        ((List<Teacher>)message.getObjList()).get(0).getPassword().equals(password)){
                    TeacherApp teacherApp = null;
                    try {
                        teacherApp = new TeacherApp(this.client ,this.scene);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }

    public static void main(String[] args) {
        launch();
    }

}