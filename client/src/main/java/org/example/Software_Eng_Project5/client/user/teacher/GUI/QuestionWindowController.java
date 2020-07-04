package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionWindowController {
        private Stage stage;
        private Profession profession;
        private String courseCode;
        private List<Course> courseList;
        private Question question;
        private boolean isQuestionUsed;
        private boolean isEdit;

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
        private VBox coursesVBox;



    @FXML
        private void clickAns(ActionEvent event) {
            String answer = ((CheckBox)event.getSource()).getId();
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

            if(this.isEdit && !this.isQuestionUsed) {
                message.setSingleObject(this.question);
                EventBus.getDefault().post(new TeacherEvent(message, "Update Question"));
                return;
            }


            message.setSingleObject(this.profession);
            message.setObjList2(this.courseList);
            EventBus.getDefault().post(new TeacherEvent(message, "Create Question"));
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
            else if (this.courseList.isEmpty() && (!isEdit || isQuestionUsed))
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

    public void showCourses() {

        this.courseList = new ArrayList<>();

        Image professionImg = new Image("org\\example\\Software_Eng_Project5\\client\\user\\icons\\profession.png");
        ImageView professionIcon = new ImageView(professionImg);
        TreeItem<HBox> professionTreeItem = new TreeItem<>( new HBox(
                new Label(this.profession.getCode() + " " + this.profession.getName())), professionIcon);
        TreeView<HBox> professionNode = new TreeView<>(professionTreeItem);
        professionNode.setStyle("-fx-background-color:  #1D1D1F");
        professionNode.setStyle("-fx-control-inner-background:  #313335");

        Image courseImg = new Image("org\\example\\Software_Eng_Project5\\client\\user\\icons\\course.png");
        ImageView courseIcon;
        TreeItem<HBox> courseTreeItem;
        CheckBox courseCheckBox;
        Course courseObj;

        List<Course> courseList = this.profession.getCourseList();
        for (Course course : courseList) {
            courseObj = course;
            courseIcon = new ImageView(courseImg);
            courseCheckBox = new CheckBox(courseObj.getCode() + " " + courseObj.getName());

            if(isEdit && !isQuestionUsed)
                courseCheckBox.setDisable(true);
            else
                courseCheckBox.setOnAction(this::selectCourses);

            courseTreeItem = new TreeItem<>(new HBox(courseCheckBox), courseIcon);
            professionTreeItem.getChildren().add(courseTreeItem);
        }

        professionTreeItem.setExpanded(true);
        coursesVBox.getChildren().add(professionNode);
    }

    private void selectCourses(ActionEvent event){
        CheckBox checkBox = (CheckBox)event.getSource();
        String selectedCourseCode = checkBox.getText().substring(0, 2);
        List<Course> courseList = this.profession.getCourseList();
        Course selectedCourse = null;

        for (Course course: courseList){
            if (selectedCourseCode.equals(course.getCode()))
                selectedCourse = course;
        }

        if(checkBox.isSelected()){
            this.courseList.add(selectedCourse);
        }
        else
            this.courseList.remove(selectedCourse);

    }

    public void setParameters(Stage stage, Profession profession, Question question, boolean isQuestionUsed, boolean isEdit) {
        this.stage = stage;
        this.profession = profession;
        this.question = question;
        this.isQuestionUsed = isQuestionUsed;
        this.isEdit = isEdit;

        this.showCourses();
        if(isEdit)
            this.initializedQuestion();
    }

    private void initializedQuestion() {
        this.questionTextArea.setText(this.question.getQuestionText());
        List<Answer> answerList = this.question.getAnswers();
        this.answer1Text.setText(answerList.get(0).getAnsText());
        this.answer2Text.setText(answerList.get(1).getAnsText());
        this.answer3Text.setText(answerList.get(2).getAnsText());
        this.answer4Text.setText(answerList.get(3).getAnsText());
        switch (this.question.getCorrectAnsNum()){
            case 1:
                chooseAnswer1.setSelected(true);
                break;
            case 2:
                chooseAnswer2.setSelected(true);
                break;
            case 3:
                chooseAnswer3.setSelected(true);
                break;
            case 4:
                chooseAnswer4.setSelected(true);
                break;
        }
        if(this.isQuestionUsed)
            this.errorLabel.setText("The question is contained in a test, you can create " +
                    "a new question based on this one.");
    }

    @Subscribe
    public void inTeacherEvent(TeacherEvent event) {
        String eventType = event.getEventType();

        if (eventType.startsWith("Created") || eventType.startsWith("Updated")) {
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("messageWindow.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (eventType.equals("Created Question"))
                    ((messageWindowController) fxmlLoader.getController()).setMessage("A new question was\ncreated and saved.");
                else if(eventType.equals("Updated Question"))
                    ((messageWindowController) fxmlLoader.getController()).setMessage("The question\nwas edited.");
                stage.setScene(scene);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.close();
            });
        }
    }

}

