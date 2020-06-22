package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.util.List;


public class MainTeacherController {

    private boolean isShowQuestions;
    private String professionCode;
    private String courseCode;
    private Profession profession;
    private Course course;
    private List<Profession> professionList;
    private  List<Question> questionList;
    private  CreateQuestionController createQuestionController;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label userNameLabel;
    @FXML
    private Button showQuestionsB;
    @FXML
    private VBox professionVBox;
    @FXML
    private VBox contentVBox;
    @FXML
    private Label contentTitle;


    @FXML
    private javafx.scene.layout.VBox VBox;

    public void setUserName(String userName){
        userNameLabel.setText(userName);
    }

    public void showProfessions(){
        isShowQuestions = false;
        Image professionImg = new Image("org\\example\\Software_Eng_Project5\\client\\user\\icons\\profession.png");
        Image courseImg = new Image("org\\example\\Software_Eng_Project5\\client\\user\\icons\\course.png");
        ImageView professionIcon;
        ImageView courseIcon;
        TreeView<String> professionNode;
        TreeItem<String> professionTreeItem;
        TreeItem<String> courseTreeItem;
        Profession professionObj;
        List<Course> courseList;
        Course courseObj;

        for (Profession profession : professionList) {
            professionObj = profession;
            professionIcon = new ImageView(professionImg);
            professionTreeItem = new TreeItem<String>(professionObj.getCode() + " " + professionObj.getName(), professionIcon);
            courseList = professionObj.getCourseList();

            for (Course course : courseList) {
                courseObj = course;
                courseIcon = new ImageView(courseImg);
                courseTreeItem = new TreeItem<String>(courseObj.getCode() + " " + courseObj.getName(), courseIcon);
                professionTreeItem.getChildren().add(courseTreeItem);
            }

            professionNode = new TreeView<>(professionTreeItem);
            professionNode.setStyle("-fx-background-color:  #1D1D1F");
            professionNode.setStyle("-fx-control-inner-background:  #313335");

            TreeView<String> finalProfessionNode = professionNode;
            professionNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
                TreeItem<String> item = finalProfessionNode.getSelectionModel().getSelectedItem();
                if (item != null) {
                    String name = item.getValue();
                    if (item.getParent() != null) {
                        setProfessionCode(item.getParent().getValue().substring(0, 2));
                        setCourseCode(name.substring(0, 2));
                    }
                    else
                        setProfessionCode(name.substring(0, 2));
                    if (this.isShowQuestions)
                        showQuestionsB(null);
                }

            });
            professionVBox.getChildren().add(professionNode);
        }
        TreeItem<String> teacherQuestions = new TreeItem<>("My Questions");
        TreeView<String> teacherQuestionsNode = new TreeView<>(teacherQuestions);
        teacherQuestionsNode.setStyle("-fx-background-color:  #1D1D1F");
        teacherQuestionsNode.setStyle("-fx-control-inner-background:  #313335");

        teacherQuestionsNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
            setCourseCode(null);
            setProfessionCode(null);
            if (this.isShowQuestions)
                showQuestionsB(null);
        });
        professionVBox.getChildren().add(teacherQuestionsNode);
    }

    @FXML
    private void showQuestionsB(ActionEvent event){
        this.isShowQuestions = true;
        this.contentVBox.getChildren().clear();
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("Question");

        if (this.courseCode != null) {
            this.contentTitle.setText("The questions of course " + this.course.getCode() + " " + this.course.getName()
                    + " in profession " + this.profession.getCode() + " " + this.profession.getName());
            msg.setClassType(Course.class);
            msg.setIndexString(this.courseCode);
        }

        else if(this.professionCode != null) {
            this.contentTitle.setText("The questions of profession " + this.profession.getCode() + " " + this.profession.getName());
            msg.setClassType(Profession.class);
            msg.setIndexString(this.professionCode);
        }

        else{
            this.contentTitle.setText("My Questions");
            msg.setClassType(Teacher.class);
            msg.setIndexString(this.userNameLabel.getText());
        }

        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));
        questionList = null;
        do {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (questionList == null);

        if(!questionList.isEmpty())
            System.out.println("Showing the Questions");
        else {
            Label label = new Label("There are no questions yet.");
            label.setTextFill(Color.WHITE);
            this.contentVBox.getChildren().add(label);
        }

        if (this.professionCode != null){
            Button addQuestion = new Button("Create Question");
            addQuestion.setOnAction((this::onCreateQuestion));
            this.contentVBox.getChildren().add(addQuestion);
        }
    }

    @FXML
    private void onCreateQuestion(ActionEvent event){
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("createQuestionWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("HSTS");
        Image appIcon = new Image("\\org\\example\\Software_Eng_Project5\\client\\user\\icons\\question_black.png");
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
        this.createQuestionController = fxmlLoader.getController();
        EventBus.getDefault().register(this.createQuestionController);
        CreateQuestionController.setStage(stage);
        this.createQuestionController.showCourses(this.profession);
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
        this.courseCode = null;
        for (Profession profession: professionList){
            if(profession.getCode().equals(professionCode))
                this.profession = profession;
        }
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        List<Course> courses = this.profession.getCourseList();
        for (Course course: courses){
            if(course.getCode().equals(courseCode))
                this.course = course;
        }
    }

    public List<Profession> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<Profession> professionList) {
        this.professionList = professionList;
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inTeacherEvent(TeacherEvent event){
        Message message = event.getMessage();
        String eventType = event.getEventType();

        if (eventType.equals("Received")){
            if(message.getItemsType().equals("Question") && message.isList()) {
                questionList = (List<Question>) message.getObjList();
                System.out.println("---------2");
            }
        }



    }
}

