package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.example.Software_Eng_Project5.entities.Course;
import org.example.Software_Eng_Project5.entities.Profession;
import org.example.Software_Eng_Project5.entities.Question;
import org.greenrobot.eventbus.EventBus;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class CreateQuestionController {
        static private Stage stage;
        private Profession profession;
        private String courseCode;
        private List<Course> courseList;

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
            else if (this.courseList.isEmpty())
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

    public void showCourses(Profession professionObj) {
        this.profession = professionObj;
        this.courseList = new ArrayList<>();

        Image professionImg = new Image("org\\example\\Software_Eng_Project5\\client\\user\\icons\\profession.png");
        ImageView professionIcon = new ImageView(professionImg);
        TreeItem<HBox> professionTreeItem = new TreeItem<HBox>( new HBox(
                new Label(professionObj.getCode() + " " + professionObj.getName())), professionIcon);
        TreeView<HBox> professionNode = new TreeView<>(professionTreeItem);
        professionNode.setStyle("-fx-background-color:  #1D1D1F");
        professionNode.setStyle("-fx-control-inner-background:  #313335");

        Image courseImg = new Image("org\\example\\Software_Eng_Project5\\client\\user\\icons\\course.png");
        ImageView courseIcon;
        TreeItem<HBox> courseTreeItem;
        CheckBox courseCheckBox;
        Course courseObj;

        List<Course> courseList = professionObj.getCourseList();
        for (Course course : courseList) {
            courseObj = course;
            courseIcon = new ImageView(courseImg);
            courseCheckBox = new CheckBox(courseObj.getCode() + " " + courseObj.getName());
            courseCheckBox.setOnAction(this::selectCourses);
            courseTreeItem = new TreeItem<HBox>(new HBox(courseCheckBox), courseIcon);
            professionTreeItem.getChildren().add(courseTreeItem);
        }

        coursesVBox.getChildren().add(professionNode);
    }

    public static void setStage(Stage stage) {
        CreateQuestionController.stage = stage;
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


        for (Course course: this.courseList){
            System.out.println(course.getCode() + " " + course.getName());
        }
    }

    @Subscribe
    public void inTeacherEvent(TeacherEvent event){
        if(event.getEventType().equals("Created")){
            if (event.getMessage().getItemsType().equals("Question"))
                System.out.println("Save");
            stage.close();
        }
    }
}

