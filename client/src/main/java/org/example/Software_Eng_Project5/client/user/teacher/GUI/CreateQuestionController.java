package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.greenrobot.eventbus.EventBus;
import org.example.Software_Eng_Project5.entities.Message;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionController {

        static public Stage stage;

        @FXML
        private TextArea questionTextArea;

        @FXML
        private CheckBox chooseAnswer1;

        @FXML
        private TextField answer1Text;

        @FXML
        private CheckBox chooseAnswer2;

        @FXML
        private TextField answer2Text;

        @FXML
        private CheckBox chooseAnswer3;

        @FXML
        private TextField answer3Text;

        @FXML
        private CheckBox chooseAnswer4;

        @FXML
        private TextField answer4Text;

        @FXML
        private Button saveQuestionB;

        @FXML
        private Label errorLabel;

        @FXML
        private void clickAns(ActionEvent event) {
            String answer = ((CheckBox)event.getSource()).getText();
            chooseAnswer1.setSelected(false);
            chooseAnswer2.setSelected(false);
            chooseAnswer3.setSelected(false);
            chooseAnswer4.setSelected(false);
            if(answer.endsWith("1"))
                chooseAnswer1.setSelected(true);
            if(answer.endsWith("2"))
                chooseAnswer2.setSelected(true);
            if(answer.endsWith("3"))
                chooseAnswer3.setSelected(true);
            if(answer.endsWith("4"))
                chooseAnswer4.setSelected(true);
        }

        @FXML
        private void saveQuestion(ActionEvent event) {
            if(!fieldsCheck()) {
                errorLabel.setText("Error: One of the fields is empty");
                return;
            }

            Message message = new Message();
            message.setIndexInt(getCorrectAnswerNumber());
            List<String> textList = new ArrayList<>();
            textList.add(questionTextArea.getText());
            textList.add(answer1Text.getText());
            textList.add(answer2Text.getText());
            textList.add(answer3Text.getText());
            textList.add(answer4Text.getText());
            message.setObjList(textList);

            EventBus.getDefault().post(new TeacherEvent(message, "Create Question"));

            System.out.println("Save");
            stage.close();
        }

        private boolean fieldsCheck(){
            boolean check = true;
            if(answer1Text.getText().trim().isEmpty()||
                    answer2Text.getText().trim().isEmpty()||
                    answer3Text.getText().trim().isEmpty()||
                    answer4Text.getText().trim().isEmpty())
                check = false;
            else if(getCorrectAnswerNumber() == 0)
            check = false;

            return check;
        }

        private int getCorrectAnswerNumber(){
            int answerNum = 0;
            if(chooseAnswer1.isSelected())
                answerNum = 1;
            else if(chooseAnswer2.isSelected())
                answerNum = 2;
            else if(chooseAnswer3.isSelected())
                answerNum = 3;
            else if(chooseAnswer4.isSelected())
                answerNum = 4;

            return answerNum;
        }

    }

