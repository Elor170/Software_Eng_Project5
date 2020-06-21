package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.entities.Course;
import org.example.Software_Eng_Project5.entities.Profession;

import java.io.IOException;
import java.util.List;


public class MainTeacherController {

    private String professionCode;
    private String courseCode;
    private List<Profession> professionList;


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
        TreeView<String> professionNode;
        TreeItem<String> professionTreeItem;
        TreeItem<String> course;
        Profession professionObj;
        List<Course> courseList;
        Course courseObj;
        for(int i = 0; i < professionList.size();  i++){
            professionObj = professionList.get(i);
            professionTreeItem = new TreeItem<String> ("P-" + professionObj.getCode() + " " + professionObj.getName());
            courseList = professionObj.getCourseList();
            for (int j = 0; j < courseList.size(); j++){
                courseObj = courseList.get(j);
                course = new TreeItem<String> ( "C-" + courseObj.getCode() + " " + courseObj.getName());
                professionTreeItem.getChildren().add(course);
            }

            professionNode = new TreeView<>(professionTreeItem);
            professionNode.setStyle("-fx-background-color:  #1D1D1F");
            professionNode.setStyle("-fx-control-inner-background:  #313335");
            TreeView<String> finalProfessionNode = professionNode;
            professionNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
                TreeItem<String> item = finalProfessionNode.getSelectionModel().getSelectedItem();
                if(item != null){
                    String name = item.getValue();
                    if (name.startsWith("P"))
                        setProfessionCode(name.substring(2, 3));
                    if (name.startsWith("C"))
                        setCourseCode(name.substring(2, 5));
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
        });
        professionVBox.getChildren().add(teacherQuestionsNode);
    }




    @FXML
    private void showQuestionsB(ActionEvent event){
        this.contentTitle.setText("My Questions");
        if (this.professionCode != null)
            this.contentTitle.setText("The questions of profession P-" + this.professionCode);
        else if (this.courseCode != null)
            this.contentTitle.setText("The questions of course C-" + this.courseCode);

        contentVBox.getChildren().clear();

        HBox questionHBox = new HBox();
        Label questionCode = new Label("123");
        questionCode.setTextFill(Color.WHITE);
        Label questionText = new Label("??????????");
        questionText.setTextFill(Color.WHITE);
        questionHBox.getChildren().addAll(questionCode, questionText);
        contentVBox.getChildren().add(questionHBox);
  //      this.contentTitle.setTextFill(Paint.valueOf("#ffffff"));
//        this.contentTitle.setTextAlignment(TextAlignment.CENTER);
        //this.contentTitle.setText
//        contentVBox.getChildren().clear();
//        Label questionLabel = new Label(labelStr);
//        questionLabel.setTextFill(Paint.valueOf("#ffffff"));
//        contentVBox.getChildren().add(questionLabel);

//        VBox vb = new VBox();
//        Label l = new Label("Questions");
//        vb.setAlignment(Pos.TOP_CENTER);
//        l.setTextFill(Color.WHITE);
//        vb.getChildren().add(l);
//        l = new Label("Q1");
//        l.setTextFill(Color.WHITE);
//        vb.getChildren().add(l);
//        l = new Label("Q2");
//        l.setTextFill(Color.WHITE);
//        vb.getChildren().add(l);
//        l = new Label("Q3");
//        l.setTextFill(Color.WHITE);
//        vb.getChildren().add(l);
//        Button addQuestion = new Button("Create Question");
//        addQuestion.setOnAction((this::onCreateQuestion));
//        vb.getChildren().add(addQuestion);
//
//        borderPane.setCenter(vb);
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
        CreateQuestionController.stage = stage;
    }

    public String getProfessionCode() {
        return professionCode;
    }

    public void setProfessionCode(String professionCode) {
        this.professionCode = professionCode;
        this.courseCode = null;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
        this.professionCode = null;
    }

    public List<Profession> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<Profession> professionList) {
        this.professionList = professionList;
    }
}