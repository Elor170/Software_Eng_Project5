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
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainTeacherController {
    private boolean isShowQuestions;
    private boolean isShowExams;
    private boolean isShowFinishedExams;
    private String professionCode;
    private String courseCode;
    private Profession profession;
    private Course course;
    private List<Profession> professionList;
    private List<Exam> examList;
    private List<Question> questionList;
    private List<SolvedExam> solvedExamList;
    private ArrayList<String> answersList;
    private TextField examCodeText;
    private TextField newTimeText;

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
    @FXML
    private Button finishedExamsB;
    @FXML
    private Button TimeChangeB;

    public void setUserName(String userName){
        userNameLabel.setText(userName);
    }

    public void showProfessions(){
        isShowQuestions = false;
        isShowExams = false;
        isShowFinishedExams = false;
        Image professionImg = new Image("org/example/Software_Eng_Project5/client/user/icons/profession.png");
        Image courseImg = new Image("org/example/Software_Eng_Project5/client/user/icons/course.png");
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
                        showQuestions(null);
                    else if (this.isShowExams)
                        showExams(null);
                }

            });
            professionVBox.getChildren().add(professionNode);
        }
        TreeItem<String> teacherQuestions = new TreeItem<>("My Questions\\ Exams");
        TreeView<String> teacherQuestionsNode = new TreeView<>(teacherQuestions);
        teacherQuestionsNode.setStyle("-fx-background-color:  #1D1D1F");
        teacherQuestionsNode.setStyle("-fx-control-inner-background:  #313335");

        teacherQuestionsNode.addEventFilter(MouseEvent.MOUSE_CLICKED, (mouseEvent) -> {
            if(this.profession != null) {
                setCourseCode(null);
                setProfessionCode(null);
            }
            if (this.isShowQuestions)
                showQuestions(null);
            else if (this.isShowExams)
                showExams(null);
        });
        professionVBox.getChildren().add(teacherQuestionsNode);
    }

    @FXML
    private void showQuestions(ActionEvent event){
        this.isShowExams = false;
        this.isShowQuestions = true;
        this.isShowFinishedExams = false;
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
                    +  this.course.getCode() + "  " + this.course.getName() + ":  Questions" );
            msg.setClassType(Course.class);
            msg.setIndexString(this.courseCode);
        }

        else if(isProfession) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  Questions" );
            msg.setClassType(Profession.class);
            msg.setIndexString(this.professionCode);
        }

        else {
            this.contentTitle.setText("My Questions");
            msg.setClassType(Teacher.class);
            msg.setIndexString(this.userNameLabel.getText());
        }

        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));
        this.questionList = null;
        do {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.questionList == null);

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
            label.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 20 20 5 5;"
                    + "-fx-font-size: 16;");
            this.contentVBox.getChildren().add(label);
        }

        // Add create question button
        if (! isMyQuestions){
            Button addQuestion = new Button("Create Question");
            addQuestion.setStyle("-fx-background-color: #DADCE0;" + "-fx-font-size: 16;");
            addQuestion.setOnAction((this::onCreateQuestion));
            HBox hBox = new HBox(addQuestion);
            hBox.setAlignment(Pos.CENTER);
            this.contentVBox.getChildren().add(hBox);
        }
    }

    @FXML
    void showFinishedExams(ActionEvent event)
    {
        this.isShowExams = false;
        this.isShowQuestions = false;
        this.isShowFinishedExams = true;
        this.contentVBox.getChildren().clear();
        boolean isCourse = false;
        boolean isProfession = false;
//        boolean isMyExams = false;
        if (this.courseCode != null)
            isCourse = true;
        else if(this.professionCode != null)
            isProfession = true;
//        else
//            isMyExams = true;
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("FinishedExam");

        if (isCourse) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  "
                    +  this.course.getCode() + "  " + this.course.getName() + ": Finished Exams" );
            msg.setClassType(Course.class);
            msg.setIndexString(this.courseCode);
        }

        else if(isProfession) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":Finished Exams" );
            msg.setClassType(Profession.class);
            msg.setIndexString(this.professionCode);
        }

        else {
            this.contentTitle.setText("Finished Exams");
            msg.setClassType(Teacher.class);
            msg.setIndexString(this.userNameLabel.getText());
        }

        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));
        this.solvedExamList = null;

        do {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.solvedExamList == null);

        if(!this.solvedExamList.isEmpty()){
            Button showOrEditExamB;
            HBox examHBox;
            Label examCodeLabel;
            Label examTimeLabel;
            Label examTypeLabel;

            for (SolvedExam solvedExam : this.solvedExamList){
                examHBox = new HBox();
                showOrEditExamB = new Button("Show");
                showOrEditExamB.setOnAction(this::showSingleFinishedExam);

                showOrEditExamB.setStyle("-fx-background-color: #DADCE0;" + "-fx-background-insets: 5");
                Exam exam = solvedExam.getPulledExam().getOriginalExam();
                examCodeLabel = new Label("Code: " + exam.getCode());
                examCodeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                int examHours = Math.floorDiv(exam.getTestTime(),60);
                int solvedExamHours = Math.floorDiv(solvedExam.getTime(),60);
                int hours = examHours - solvedExamHours;
                String minutes = Integer.toString( (exam.getTestTime() - 60 * examHours) - (solvedExam.getTime() - 60 * solvedExamHours));
                if(minutes.length() < 2)
                    minutes = "0" + minutes;
                examTimeLabel = new Label(  "Execution Time: " + hours + ":" + minutes);
                examTimeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                examTypeLabel = new Label();
                examTypeLabel.setText("Student: " + solvedExam.getStudentName());
                examTypeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                examHBox.getChildren().addAll(showOrEditExamB, examCodeLabel, examTimeLabel, examTypeLabel);
                examHBox.setSpacing(20);
                examHBox.setStyle("-fx-font-size: 16;");
                this.contentVBox.getChildren().add(examHBox);
            }
        }

        // Case: No exams in the selected course/profession
        else {
            Label label = new Label("There are no finished exams yet.");
            label.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 20 20 5 5;"
                    + "-fx-font-size: 16;");
            this.contentVBox.getChildren().add(label);
        }
    }

    private void showSingleFinishedExam(ActionEvent event)
    {
        System.out.println("-----edit Single Finished Exam----");
        //TODO
        String selectedExamCode = (((Label)((HBox)((Button)event.getSource()).getParent()).getChildren().get(1)).getText());
        selectedExamCode = selectedExamCode.substring(6);
        SolvedExam selectedExam = null;
        for(SolvedExam solvedExam: solvedExamList){
            if(selectedExamCode.equals(solvedExam.getPulledExam().getOriginalExam().getCode()))
                selectedExam = solvedExam;
        }
        String professionCode = selectedExam.getPulledExam().getOriginalExam().getCode().substring(0,2);
        for (Profession profession: professionList){
            if(profession.getCode().equals(professionCode))
                this.profession = profession;
        }
        this.openFinishedExamWindow(true, false, false, true, selectedExam);
    }

    private void openFinishedExamWindow(boolean isEditExam, boolean isCreatExam, boolean isShowSingleExam,
                                        boolean isShowFinished, SolvedExam selectedExam)
    {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("examWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
        ExamWindowController examControllerWindow = fxmlLoader.getController();
        EventBus.getDefault().register(examControllerWindow);
        boolean isExamUsed = false;
        if(isEditExam)
            isExamUsed = selectedExam.getPulledExam().getOriginalExam().isPulled();
        String answers = answersList.get(solvedExamList.indexOf(selectedExam));
        examControllerWindow.setParameters(stage, this.profession, null, selectedExam,
                isExamUsed, isEditExam, isCreatExam, isShowSingleExam, isShowFinished, userNameLabel.getText(), answers, Teacher.class);
    }

    @FXML
    void showExams(ActionEvent event) {
        this.isShowExams = true;
        this.isShowQuestions = false;
        this.isShowFinishedExams = false;
        this.contentVBox.getChildren().clear();
        boolean isCourse = false;
        boolean isProfession = false;
        boolean isMyExams = false;
        if (this.courseCode != null)
            isCourse = true;
        else if(this.professionCode != null)
            isProfession = true;
        else
            isMyExams = true;
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("Exam");

        if (isCourse) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  "
                    +  this.course.getCode() + "  " + this.course.getName() + ":  Exams" );
            msg.setClassType(Course.class);
            msg.setIndexString(this.courseCode);
        }

        else if(isProfession) {
            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  Exams" );
            msg.setClassType(Profession.class);
            msg.setIndexString(this.professionCode);
        }

        else {
            this.contentTitle.setText("My Exams");
            msg.setClassType(Teacher.class);
            msg.setIndexString(this.userNameLabel.getText());
        }

        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));
        this.examList = null;

        do {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.examList == null);

        //Case: There are exams in the selected course/profession
        if(!this.examList.isEmpty()){
            Button showOrEditExamB;
            HBox examHBox;
            Label examCodeLabel;
            Label examTimeLabel;
            Label examTypeLabel;

            for (Exam exam : this.examList){
                examHBox = new HBox();
                if(isMyExams){
                    showOrEditExamB = new Button("Edit");
                    showOrEditExamB.setOnAction(this::editExam);
                }
                else {
                    showOrEditExamB = new Button("Show");
                    showOrEditExamB.setOnAction(this::showSingleExam);
                }
                showOrEditExamB.setStyle("-fx-background-color: #DADCE0;" + "-fx-background-insets: 5");

                examCodeLabel = new Label("Code: " + exam.getCode());
                examCodeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                int hours = Math.floorDiv(exam.getTestTime(),60);
                String minutes = Integer.toString(exam.getTestTime() - 60 * hours);
                if(minutes.length() < 2)
                    minutes = "0" + minutes;
                examTimeLabel = new Label(  "Time: " + hours + ":" + minutes);
                examTimeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                examTypeLabel = new Label();
                if (exam.isManual())
                    examTypeLabel.setText("Type: Manual");
                else
                    examTypeLabel.setText("Type: Computerized");
                examTypeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                examHBox.getChildren().addAll(showOrEditExamB, examCodeLabel, examTimeLabel, examTypeLabel);
                examHBox.setSpacing(20);
                examHBox.setStyle("-fx-font-size: 16;");
                this.contentVBox.getChildren().add(examHBox);
            }

        }

        // Case: No exams in the selected course/profession
        else {
            Label label = new Label("There are no exams yet.");
            label.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 20 20 5 5;"
                    + "-fx-font-size: 16;");
            this.contentVBox.getChildren().add(label);
        }

        // Add create exam button
        if (!isMyExams){
            Button addExam = new Button("Create Exam");
            addExam.setStyle("-fx-background-color: #DADCE0;" + "-fx-font-size: 16;");
            addExam.setOnAction((this::onCreateExam));
            HBox hBox = new HBox(addExam);
            hBox.setAlignment(Pos.CENTER);
            this.contentVBox.getChildren().add(hBox);
        }
    }

    private void editExam(ActionEvent event) {
        System.out.println("-----edit Single Exam----");
        //TODO
        String selectedExamCode = (((Label)((HBox)((Button)event.getSource()).getParent()).getChildren().get(1)).getText());
        selectedExamCode = selectedExamCode.substring(6);
        Exam selectedExam = null;
        for(Exam exam: examList){
            if(selectedExamCode.equals(exam.getCode()))
                selectedExam = exam;
        }
        String professionCode = selectedExam.getCode().substring(0,2);
        for (Profession profession: professionList){
            if(profession.getCode().equals(professionCode))
                this.profession = profession;
        }
        this.openExamWindow(true, false, false, false, selectedExam);
    }

    private void showSingleExam(ActionEvent event) {
        String selectedExamCode = (((Label)((HBox)((Button)event.getSource()).getParent()).getChildren().get(1)).getText());
        selectedExamCode = selectedExamCode.substring(6);
        Exam selectedExam = null;
        for(Exam exam: examList){
            if(selectedExamCode.equals(exam.getCode()))
                selectedExam = exam;
        }
        this.openExamWindow(true, false, true, false, selectedExam);
    }

    private void onCreateExam(ActionEvent event) {
        this.openExamWindow(false, true, false, false,null);
    }

    private void openExamWindow(boolean isEditExam, boolean isCreatExam, boolean isShowSingleExam, boolean isShowFinished, Exam selectedExam) {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("examWindow.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("HSTS");
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
        ExamWindowController examControllerWindow = fxmlLoader.getController();
        EventBus.getDefault().register(examControllerWindow);
        boolean isExamUsed = false;
        if(isEditExam)
            isExamUsed = selectedExam.isPulled();
        examControllerWindow.setParameters(stage, this.profession, selectedExam, null,
                isExamUsed, isEditExam, isCreatExam, isShowSingleExam, isShowFinished, userNameLabel.getText(), null, Teacher.class);
    }

    private void onEditQuestion(ActionEvent event) {
        Question selectedQuestion = null;

        VBox questionVBox = (VBox)((Button)event.getSource()).getParent();
        Label questionCodeLabel = (Label) ((HBox)questionVBox.getChildren().get(0)).getChildren().get(0);
        String selectedQuestionCode = questionCodeLabel.getText();

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
        Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/question_black.png");
        stage.getIcons().add(appIcon);
        stage.setScene(scene);
        stage.show();
        QuestionWindowController questionWindowController = fxmlLoader.getController();
        EventBus.getDefault().register(questionWindowController);
        questionWindowController.setParameters(stage, this.profession, selectedQuestion, isQuestionUsed, isEditQuestion);
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

    @FXML
    void requestTimeChange(ActionEvent event)
    {
        this.contentVBox.getChildren().clear();
        HBox requestTimeHBox = new HBox();
        Button sendRequestB = new Button("Send Request");
        sendRequestB.setOnAction(this::sendRequest);
        sendRequestB.setStyle("-fx-background-color: #DADCE0;" + "-fx-background-insets: 5");
        Label examCodeLabel = new Label("Execution Code: ");
        examCodeLabel.setStyle("-fx-text-fill: #d6e0e5;");
        Label newTimeLabel = new Label("New Time: ");
        newTimeLabel.setStyle("-fx-text-fill: #d6e0e5;");
        examCodeText = new TextField();
        newTimeText = new TextField();
        requestTimeHBox.getChildren().addAll(sendRequestB, examCodeLabel, examCodeText, newTimeLabel, newTimeText);
        this.contentVBox.getChildren().add(requestTimeHBox);
    }

    private void sendRequest(ActionEvent actionEvent)
    {
        Message message = new Message();
        message.setSingleObject(examCodeText.getText());
        message.setSingleObject2(Integer.parseInt(newTimeText.getText()));

        EventBus.getDefault().post(new TeacherEvent(message, "Request Time Change"));
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inTeacherEvent(TeacherEvent event){
        Message message = event.getMessage();
        String eventType = event.getEventType();

        switch (eventType) {
            case "Received":
                String Type = message.getItemsType();
                if (Type.equals("Question") && message.isList()) {
                    this.questionList = (List<Question>) message.getObjList();
                } else if (Type.equals("Exam") && message.isList()) {
                    this.examList = (List<Exam>) message.getObjList();
                }
                else if (Type.equals("Finished Exams"))
                {
                    this.solvedExamList = (List<SolvedExam>)message.getObjList();
                    this.answersList = (ArrayList<String>)message.getObjList2();
                }
                break;
            case "Created Question":
            case "Updated Question":
                Platform.runLater(() -> this.showQuestions(null));
                break;
            case "Created Exam":
            case "Updated Exam":
                Platform.runLater(() -> this.showExams(null));
                break;
//            case "Updated Solved Exam":
//                Platform.runLater(() -> this.showFinishedExams(null));
//                break;
            case "Requested Time":
                Platform.runLater(() ->
                {
                    Stage stage = new Stage();
                    FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("messageWindow.fxml"));
                    Scene scene = null;
                    try
                    {
                        scene = new Scene(fxmlLoader.load());
                    } catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                    stage.setTitle("HSTS");
                    Image appIcon = new Image("/org/example/Software_Eng_Project5/client/user/icons/app_icon.png");
                    stage.getIcons().add(appIcon);
                    ((messageWindowController) fxmlLoader.getController()).setMessage("Time request was \n sent.");
                    stage.setScene(scene);
                    stage.show();
//                    try
//                    {
//                        Thread.sleep(2000);
//                    } catch (InterruptedException e)
//                    {
//                        e.printStackTrace();
//                    }
//                    stage.close();
                });
                break;
        }

    }
}

