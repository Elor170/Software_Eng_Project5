package org.example.Software_Eng_Project5.client.user.student.GUI;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.PulledExam;
import org.example.Software_Eng_Project5.entities.Student;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class studentSolvedExamController {

    @FXML
    private Label gradeLabel;

    @FXML
    private Label timeUpLabel;


    public void showGradeWindow(Message message) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentApp.class.getResource("studentSolvedExamWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.getIcons().add(appIcon);
        stage.show();
        timeUpLabel.setVisible(false);
        if(message.getTestTime() == 0)
            timeUpLabel.setVisible(true);
        int grade = message.getGrade();
        String msg;
        if(grade < 51)
            msg = "\nBetter Luck Next Time :(";
        else if(grade >= 51 && grade < 80)
            msg = "\nWell Done :)";
        else
            msg = "\nWOW You're Amazing!!!";
        gradeLabel.setText("Your Grade Is: " + grade + msg);
    }

}
