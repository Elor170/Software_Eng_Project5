package org.example.Software_Eng_Project5.client.user.student.GUI;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentExamController {

    private Exam originalExam;
    private PulledExam pulledExam;
    private List<Question> questionList;
    private List<CheckBox> answerCheckBoxes;
    private Stage stage;

    @FXML
    private Label examCodeLabel;

    @FXML
    private VBox contentVbox;

    @FXML
    private Button endExamB;

    @FXML
    private Label hoursLabel;

    @FXML
    private Label minutesLabel;

    @FXML
    private Label studentCommentsL;


//    @FXML
//    void initialize()
//    {
//        this.examCodeLabel.setText("hello");
//        assert examCodeLabel != null : "fx:id=\"examCodeLabel\" was not injected: check your FXML file 'studentExamWindow.fxml'.";
//    }

    Timeline examTimer = new Timeline(new KeyFrame(Duration.seconds(60), new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            //while(!hoursLabel.getText().equals("0") && !minutesLabel.getText().equals("0")
            //{
                if(Integer.parseInt(minutesLabel.getText()) - 1 < 0)
                {
                    minutesLabel.setText(Integer.toString(59));
                    if(Integer.parseInt(hoursLabel.getText()) - 1 >= 0)
                        hoursLabel.setText(Integer.toString(Integer.parseInt(hoursLabel.getText()) - 1));
                }
                else
                {
                    minutesLabel.setText(Integer.toString(Integer.parseInt(minutesLabel.getText()) - 1));
                    if(hoursLabel.getText().equals("0") && minutesLabel.getText().equals("0")){
                        {
//                            try
//                            {
//
//                                Thread.sleep(5000);
//                            } catch (InterruptedException e)
//                            {
//                                e.printStackTrace();
//                            }
                            endExamB.fire();
                        }
                    }
                }

           // }
        }
    }));

    @FXML
    public void startExam(PulledExam pulledExam) throws IOException
    {
        FXMLLoader fxmlLoader = new FXMLLoader(StudentApp.class.getResource("studentExamWindow.fxml"));
        fxmlLoader.setController(this);
        Parent root = fxmlLoader.load();
        stage = new Stage();
        stage.setScene(new Scene(root));
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.getIcons().add(appIcon);
        stage.show();
        Exam originalExam = pulledExam.getOriginalExam();
        examCodeLabel.setText("Exam code: " + originalExam.getCode());
        examCodeLabel.setStyle("-fx-font-weight: bold;");
        studentCommentsL.setText(pulledExam.getOriginalExam().getTextForStudent());
        int hours = Math.floorDiv(originalExam.getTestTime(),60);
        hoursLabel.setText(Integer.toString(hours));
        String minutes = Integer.toString(originalExam.getTestTime() - 60 * hours);
        minutesLabel.setText(minutes);
//        this.contentVbox.getChildren().clear();
//        contentVbox = new VBox();
        this.pulledExam = pulledExam;
        this.originalExam = pulledExam.getOriginalExam();
        this.questionList = this.originalExam.getQuestionList();
        VBox questionVBox;
        HBox topQuestionHBox;
        Label codeLabel;
        Label textLabel;
//        VBox answersVBox;
        CheckBox answerCheckBox;
        List<Answer> answers;
        answerCheckBoxes = new ArrayList<>();
        for (Question question: this.questionList){
            questionVBox = new VBox();
            codeLabel = new Label(question.getCode().substring(3));
            textLabel = new Label(" " + question.getQuestionText());
            topQuestionHBox = new HBox(codeLabel, textLabel);
            questionVBox.getChildren().add(topQuestionHBox);
            answers = question.getAnswers();
            for (Answer answer: answers){
                answerCheckBox = new CheckBox(answer.getAnsText());
                answerCheckBoxes.add(answerCheckBox);
                questionVBox.getChildren().add(answerCheckBox);
            }

            this.contentVbox.getChildren().add(questionVBox);
        }
        examTimer.setCycleCount(originalExam.getTestTime() * 60);
        examTimer.play();
    }

    @FXML
    private void onEndExam(ActionEvent event) {
        examTimer.stop();
        int remainingTime = Integer.parseInt(hoursLabel.getText()) * 60 + Integer.parseInt(minutesLabel.getText());
        Message message = new Message();
        message.setSingleObject(pulledExam);
        List<Integer> studentAnswers = new ArrayList<>();
        int ans = 1;
        for(int i = 0; i < answerCheckBoxes.size(); i++)
        {
            if(answerCheckBoxes.get(i).isSelected())
                studentAnswers.add(ans);
            else
                studentAnswers.add(0);
            if(ans + 1 > 4)
                ans = 1;
            else
                ans++;
        }
        message.setObjList(studentAnswers);
        message.setTestTime(remainingTime);
        stage.close();
        EventBus.getDefault().post(new StudentEvent(message, "Create Solved Exam"));
    }

    public void changeTime(String examCode, int newTime)
    {
        if(pulledExam.getExecutionCode().equals(examCode))
        {
            examTimer.stop();
            int hours = Math.floorDiv(newTime,60);
            String minutes = Integer.toString(newTime - 60 * hours);
            hoursLabel.setText(Integer.toString(hours));
            minutesLabel.setText(minutes);
            examTimer.setCycleCount(newTime);
            examTimer.play();
        }
    }
}