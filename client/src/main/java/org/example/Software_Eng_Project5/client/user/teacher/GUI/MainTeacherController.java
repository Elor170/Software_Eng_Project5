package org.example.Software_Eng_Project5.client.user.teacher.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
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
    private VBox VBox;


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
            professionTreeItem = new TreeItem<>(professionObj.getCode() + " " + professionObj.getName(), professionIcon);
            courseList = professionObj.getCourseList();

            for (Course course : courseList) {
                courseObj = course;
                courseIcon = new ImageView(courseImg);
                courseTreeItem = new TreeItem<>(courseObj.getCode() + " " + courseObj.getName(), courseIcon);
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
            if(this.profession != null) {
                setCourseCode(null);
                setProfessionCode(null);
            }
            if (this.isShowQuestions)
                showQuestionsB(null);
        });
        professionVBox.getChildren().add(teacherQuestionsNode);
    }

    @FXML
    private void showQuestionsB(ActionEvent event){
        this.isShowQuestions = true;
        this.contentVBox.getChildren().clear();
        boolean isCourse = false;
        boolean isProfession = false;
        boolean isMyQuestions = false;
        if (this.courseCode != null)
            isCourse = true;
        else if(this.professionCode != null)
            isProfession = true;
        else
            isMyQuestions = true;
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("Question");

        if (isCourse) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  "
                    +  this.course.getCode() + "  " + this.course.getName() + ":  Questions:" );
            msg.setClassType(Course.class);
            msg.setIndexString(this.courseCode);
        }

        else if(isProfession) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  Questions:" );
            msg.setClassType(Profession.class);
            msg.setIndexString(this.professionCode);
        }

        else {
            this.contentTitle.setText("My Questions");
            msg.setClassType(Teacher.class);
            msg.setIndexString(this.userNameLabel.getText());
        }

        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));
        questionList = null;
        do {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (questionList == null);

        //Case: There are questions in the selected course/profession
        if(!questionList.isEmpty()){
            VBox questionVBox;
            HBox questionTopHBox;
            Label questionTextLabel;
            Label questionCodeLabel;
            HBox answerHBox;
            Label answerLabel;
            Circle answerCircle;

            for(Question question: questionList) {
                questionVBox = new VBox();
                questionVBox.setStyle("-fx-padding: 10");
                List<Answer> answerList = question.getAnswers();
                int correctAnswerNum = question.getCorrectAnsNum();

                questionCodeLabel = new Label(question.getCode());
                questionCodeLabel.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-font-size: 16;");

                questionTextLabel = new Label(question.getQuestionText());
                questionTextLabel.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 0 0 0 3;"
                        + "-fx-font-size: 16;");


                questionTopHBox = new HBox(questionCodeLabel, questionTextLabel);
                questionVBox.getChildren().add(questionTopHBox);

                for(Answer answer: answerList){
                    answerLabel = new Label( answer.getAnsText());
                    answerLabel.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 0 0 0 3;"
                            + "-fx-font-size: 16;");
                    answerCircle = new Circle();
                    answerCircle.setRadius(7);

                    if(--correctAnswerNum == 0)
                        answerCircle.setStyle("-fx-fill: #499C54;");
                    else
                        answerCircle.setStyle("-fx-fill: #FF5641;");

                    answerHBox = new HBox(answerCircle, answerLabel);
                    answerHBox.setStyle("-fx-padding: 0 0 0 100");
                    questionVBox.getChildren().add(answerHBox);
                }

                if(isMyQuestions){
                    Button editQuestionB = new Button("Edit Question");
                    editQuestionB.setStyle("-fx-background-color: #DADCE0;" + "-fx-padding: 10 10 5 100;" +
                            "-fx-border-insets: 5 5 0 90;" + "-fx-background-insets: 5 5 0 90");
                    editQuestionB.setOnAction(this::onEditQuestion);
                    questionVBox.getChildren().add(editQuestionB);
                }
                contentVBox.getChildren().add(questionVBox);
            }
        }
        // Case: No questions in the selected course/profession
        else {
            Label label = new Label("There are no questions yet.");
            label.setTextFill(Color.WHITE);
            this.contentVBox.getChildren().add(label);
        }

        // Add create question button
        if (! isMyQuestions){
            Button addQuestion = new Button("Create Question");
            addQuestion.setStyle("-fx-background-color: #DADCE0;");
            addQuestion.setOnAction((this::onCreateQuestion));
            HBox hBox = new HBox(addQuestion);
            hBox.setAlignment(Pos.CENTER);
            this.contentVBox.getChildren().add(hBox);
        }
    }

    private void onEditQuestion(ActionEvent event) {
        Question selectedQuestion = null;
        System.out.println("Edit the Question");
        VBox questionVBox = (VBox)((Button)event.getSource()).getParent();
        Label questionCodeLabel = (Label) ((HBox)questionVBox.getChildren().get(0)).getChildren().get(0);
        String selectedQuestionCode = questionCodeLabel.getText();
        System.out.println("---" + selectedQuestionCode + "---");
        for(Question question: questionList){
            if(question.getCode().equals(selectedQuestionCode))
                selectedQuestion = question;
        }

        assert selectedQuestion != null;
        String professionCode = selectedQuestion.getCode().substring(0, 2);
        for(Profession profession: this.professionList){
            if(profession.getCode().equals(professionCode))
                this.profession = profession;
        }

        openQuestionWindow(selectedQuestion, selectedQuestion.isUsed(), true);
    }

    private void openQuestionWindow(Question selectedQuestion, boolean isQuestionUsed, boolean isEditQuestion) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("QuestionWindow.fxml"));
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
        QuestionControllerWindow questionControllerWindow = fxmlLoader.getController();
        //TODO check if can remove
        EventBus.getDefault().register(questionControllerWindow);
        //
        questionControllerWindow.setParameters(stage, this.profession, selectedQuestion, isQuestionUsed, isEditQuestion);
    }

    @FXML
    private void onCreateQuestion(ActionEvent event){
        openQuestionWindow(null, false, false);
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
            }
        }
        else if (eventType.equals("Created") || eventType.equals("Updated")){
            Platform.runLater( () -> this.showQuestionsB(null) );
        }


    }
}

