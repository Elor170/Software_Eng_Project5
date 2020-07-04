package org.example.Software_Eng_Project5.client.user.student.GUI;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.entities.Exam;
import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.entities.SolvedExam;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;

public class studentGradesWindowController
{
    @FXML
    private VBox containerVBox;

    public void seeExams(List<SolvedExam> solvedExams) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentApp.class.getResource("studentGradesWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.getIcons().add(appIcon);
        stage.show();
        GridPane gridpane = new GridPane();
        if(solvedExams != null)
        {
            int i = 0;
            for(SolvedExam solvedExam : solvedExams)
            {
                Label examLabel = new Label();
                Exam originalExam =  solvedExam.getPulledExam().getOriginalExam();
                examLabel.setText("Exam: " + originalExam.getCode() + ", Course: " + originalExam.getCourseName() +
                        ", Grade: " + solvedExam.getGrade());
                gridpane.add(examLabel, 2, i++);
            }
        }
        else
        {
            Label noExams = new Label();
            noExams.setText("No executed exams yet");
            gridpane.add(noExams, 2, 0);
        }
        containerVBox.getChildren().add(gridpane);
    }
}
