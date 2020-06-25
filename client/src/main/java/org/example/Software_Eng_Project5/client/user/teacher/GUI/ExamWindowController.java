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

public class ExamWindowController {
    private Stage stage;
    private Profession profession;
    private Course course;
    private List<Course> courseList;
    private List<Question> questionList;
    private List<Question> selectedQuestionList;
    private Exam exam;
    private boolean isExamUsed;
    private boolean isEdit;
    private boolean isCreat;
    private boolean isShow;
    private List<CheckBox> courseCheckBoxList;
    private List<Integer> questionsPoints;
    private List<TextField> pointsTextFields;

    @FXML
    private VBox coursesVBox;
    @FXML
    private Label examTitle;
    @FXML
    private VBox contentVBox;
    @FXML
    private TextField examTimeTF;
    @FXML
    private CheckBox isManualCheckBox;
    @FXML
    private TextArea teacherCommentsTA;
    @FXML
    private TextArea studentCommentsTA;
    @FXML
    private HBox buttonsHBox;
    @FXML
    private Button pullExamB;
    @FXML
    private Label errorLabel;

    @FXML
    void pullExam(ActionEvent event) {
        System.out.println("---------");
        Label execCodeL = new Label("Execution Code:");
        TextField execCodeTF = new TextField();
        buttonsHBox.getChildren().add(execCodeL);
        buttonsHBox.getChildren().add(execCodeTF);
        pullExamB.setText("Pull now");
        pullExamB.setOnAction(this::pullNow);

    }

    private void pullNow(ActionEvent event) {
        String execCode = ((TextField)buttonsHBox.getChildren().get(2)).getText();
        Message message = new Message();
        message.setSingleObject(this.exam);
        message.setSingleObject2(execCode);
        EventBus.getDefault().post(new TeacherEvent(message, "Pull Exam"));
    }

    public void setParameters(Stage stage, Profession profession
            ,Exam exam, boolean isExamUsed, boolean isEdit, boolean isCreat, boolean isShow) {
        this.stage = stage;
        this.profession = profession;
        this.exam = exam;
        this.isExamUsed = isExamUsed;
        this.isEdit = isEdit;
        this.isCreat = isCreat;
        this.isShow = isShow;



        if(isCreat){
            this.showCourses();
            this.examTitle.setText("New Exam");
            this.selectedQuestionList = new ArrayList<>();
            pullExamB.setText("Save");
            pullExamB.setOnAction(this::saveExam);
        }
        else if(isShow){
            this.examTitle.setText(profession.getCode() + " " + profession.getName() + ": " + exam.getCode() + ":");
            int hours = Math.floorDiv(exam.getTestTime(),60);
            String minutes = Integer.toString(exam.getTestTime() - 60 * hours);
            if(minutes.length() < 2)
                minutes = "0" + minutes;
            this.examTimeTF.setText(hours + ":" + minutes);
            this.examTimeTF.setEditable(false);
            this.studentCommentsTA.setText(exam.getTextForStudent());
            this.studentCommentsTA.setEditable(false);
            this.teacherCommentsTA.setText(exam.getTextForTeacher());
            this.teacherCommentsTA.setEditable(false);
            this.isManualCheckBox.setSelected(exam.isManual());
            this.isManualCheckBox.setDisable(true);
            this.questionList = exam.getQuestionList();
            this.showQuestions();
            this.bringGrades();
            List<Integer> questionsPoints;
            int i = 0;
            for(TextField pointsTF: pointsTextFields){
                pointsTF.setText(Integer.toString(this.questionsPoints.get(i)));
                pointsTF.setEditable(false);
                i++;
            }
            pullExamB.setOnAction(this::pullExam);
        }
        else if(isEdit && !isExamUsed){
            this.selectedQuestionList = (exam.getQuestionList());
            this.questionList = (exam.getQuestionList());
            this.examTitle.setText(exam.getCode() + ":");
            this.examTimeTF.setText(Integer.toString(exam.getTestTime()));
            this.studentCommentsTA.setText(exam.getTextForStudent());
            this.teacherCommentsTA.setText(exam.getTextForTeacher());
            this.isManualCheckBox.setSelected(exam.isManual());
            this.isManualCheckBox.setDisable(true);
            this.questionList = exam.getQuestionList();
            this.showQuestions();
            this.bringGrades();
            List<Integer> questionsPoints;
            int i = 0;
            for(TextField pointsTF: pointsTextFields){
                pointsTF.setText(Integer.toString(this.questionsPoints.get(i)));
                i++;
            }
            this.pullExamB.setText("Update");
            this.pullExamB.setOnAction(this::updateExam);
        }
        else if(isEdit && isExamUsed){
            this.selectedQuestionList = (exam.getQuestionList());
            this.questionList = (exam.getQuestionList());
            this.showCourses();
            this.examTitle.setText("New Exam");
            this.errorLabel.setText("The exam is pulled, you can create new exam based on this one.");
            this.selectedQuestionList = new ArrayList<>();
            this.examTimeTF.setText(Integer.toString(exam.getTestTime()));
            this.studentCommentsTA.setText(exam.getTextForStudent());
            this.teacherCommentsTA.setText(exam.getTextForTeacher());
            this.isManualCheckBox.setSelected(exam.isManual());
            this.questionList = exam.getQuestionList();
            this.showQuestions();
            this.bringGrades();
            List<Integer> questionsPoints;
            int i = 0;
            for(TextField pointsTF: pointsTextFields){
                pointsTF.setText(Integer.toString(this.questionsPoints.get(i)));
                i++;
            }
            for (CheckBox checkBox: courseCheckBoxList){
                String courseCode = checkBox.getText().substring(0,2);
                String courseExamCode = this.exam.getCode().substring(2,4);
                if(courseCode.equals(courseExamCode))
                    checkBox.setSelected(true);
                checkBox.setDisable(true);
            }
            pullExamB.setText("Save");
            pullExamB.setOnAction(this::updateExam);
            this.isManualCheckBox.setSelected(exam.isManual());
            this.isManualCheckBox.setDisable(true);
        }
    }

    private void updateExam(ActionEvent event) {
        errorLabel.setText(" ");
        this.questionsPoints = new ArrayList<>();
        try {
            Integer.parseInt(examTimeTF.getText());
        }catch (Exception e){
            errorLabel.setText("One of the fields is empty.");
            return;
        }
        if(examTimeTF.getText().equals("")){
            errorLabel.setText("One of the fields is empty.");
            return;
        }

        int sum = 0;
        List<TextField> pointsTF = this.pointsTextFields;
        for (TextField textField: pointsTF) {
            if(!textField.getText().equals("")){
                try {
                    Integer.parseInt(textField.getText());
                }catch (Exception e){
                    errorLabel.setText("One of the fields is wrong.");
                    return;
                }
                sum = sum + Integer.parseInt(textField.getText());
                this.questionsPoints.add(Integer.parseInt(textField.getText()));
            }
        }
        if(sum != 100){
            errorLabel.setText("The sum of the points is not 100");
            this.questionsPoints.clear();
            return;
        }



        Message message = new Message();
        message.setSingleObject(this.exam);
        message.setObjList(this.selectedQuestionList);
        List<String> textList = new ArrayList<>();
        textList.add(this.studentCommentsTA.getText());
        textList.add(this.teacherCommentsTA.getText());
        message.setObjList2(textList);
        List<Integer> tempPointsList = new ArrayList<>(this.questionsPoints);
        message.setObjList3(tempPointsList);
        message.setTestTime(Integer.parseInt(this.examTimeTF.getText()));

        EventBus.getDefault().post(new TeacherEvent(message,"Update Exam"));
    }

    private void saveExam(ActionEvent event) {
        errorLabel.setText(" ");
        if(!this.checkFields()){
            errorLabel.setText("One of the fields is empty.");
            return;
        }

        int sum = 0;
        List<TextField> pointsTF = this.pointsTextFields;
        for (TextField textField: pointsTF) {
            if(!textField.getText().equals("")){
                try {
                    Integer.parseInt(textField.getText());
                }catch (Exception e){
                    errorLabel.setText("One of the fields is wrong.");
                    return;
                }
                sum = sum + Integer.parseInt(textField.getText());
                this.questionsPoints.add(Integer.parseInt(textField.getText()));
            }
        }
        if(sum != 100){
            errorLabel.setText("The sum of the points is not 100");
            this.questionsPoints.clear();
            return;
        }



        Message message = new Message();
        message.setObjList(this.selectedQuestionList);
        List<String> textList = new ArrayList<>();
        textList.add(this.studentCommentsTA.getText());
        textList.add(this.teacherCommentsTA.getText());
        message.setObjList2(textList);
        message.setObjList3(this.questionsPoints);
        message.setSingleObject(this.profession);
        message.setSingleObject2(this.course);
        message.setTestTime(Integer.parseInt(this.examTimeTF.getText()));
        message.setManual(isManualCheckBox.isSelected());

        EventBus.getDefault().post(new TeacherEvent(message,"Create Exam"));
    }

    private boolean checkFields() {
        boolean check = true;

        if ((this.course == null) || (this.selectedQuestionList.isEmpty()) || (examTimeTF.getText().equals("")))
            check = false;

        try {
            Integer.parseInt(examTimeTF.getText());
        }catch (Exception e){
            check = false;
        }

        return check;
    }

    private void bringGrades() {
        Message msg = new Message();
        msg.setSingleObject(this.exam.getCode());
        msg.setItemsType("Grades");
        msg.setList(true);
        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));

        this.questionsPoints = null;
        do {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.questionsPoints == null);
    }

    private void bringQuestions() {
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("Question");
        msg.setClassType(Course.class);
        msg.setIndexString(course.getCode());
        EventBus.getDefault().post(new TeacherEvent(msg,"Bring"));

        this.questionList = null;
        do {
            try {

                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.questionList == null);
        this.showQuestions();
    }

    private void showQuestions() {
        this.questionsPoints = new ArrayList<>();
        this.pointsTextFields = new ArrayList<>();
        this.contentVBox.getChildren().clear();
        //Case: There are questions in the selected course/profession
        if(!questionList.isEmpty()){
            VBox questionVBox;
            HBox questionTopHBox;
            Label questionTextLabel;
            CheckBox questionCodeCheckBox;
            HBox answerHBox;
            Label answerLabel;
            Circle answerCircle;
            TextField points;
            Label pointsLabel;

            for(Question question: questionList) {
                questionVBox = new VBox();
                questionVBox.setStyle("-fx-padding: 10");
                List<Answer> answerList = question.getAnswers();
                int correctAnswerNum = question.getCorrectAnsNum();

                questionCodeCheckBox = new CheckBox(question.getCode());
                questionCodeCheckBox.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-font-size: 16;");
                questionCodeCheckBox.setOnAction(this::selectQuestion);
                if(this.isShow){
                    questionCodeCheckBox.setSelected(true);
                    questionCodeCheckBox.setDisable(true);
                }
                if(this.isEdit) {
                    questionCodeCheckBox.setSelected(true);
                }

                questionTextLabel = new Label(question.getQuestionText());
                questionTextLabel.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 0 0 0 3;"
                        + "-fx-font-size: 16;");

                pointsLabel = new Label("Points:");
                pointsLabel.setStyle("-fx-font-size: 14");
                points = new TextField();
                points.setPrefWidth(60);
                points.setStyle("-fx-font-size: 14;");

                this.pointsTextFields.add(points);
                questionTopHBox = new HBox(pointsLabel, points, questionCodeCheckBox, questionTextLabel);
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
                    answerHBox.setStyle("-fx-padding: 0 0 0 200");
                    questionVBox.getChildren().add(answerHBox);
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

    }

    private void selectQuestion(ActionEvent event) {
        CheckBox questionCheckBox = (CheckBox)event.getSource();
        boolean isSelect = questionCheckBox.isSelected();
        String questionCode = questionCheckBox.getText();
        Question selectedQuestion = null;

        for(Question question: this.questionList){
            if(questionCode.equals(question.getCode()))
                selectedQuestion = question;
        }

        if(isSelect)
            this.selectedQuestionList.add(selectedQuestion);
        else
            this.selectedQuestionList.remove(selectedQuestion);

    }

    public void showCourses() {
        this.courseList = new ArrayList<>();
        this.courseCheckBoxList = new ArrayList<>();

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

            if(isEdit && !this.isExamUsed)
                courseCheckBox.setDisable(true);
            else
                courseCheckBox.setOnAction(this::selectCourses);

            courseTreeItem = new TreeItem<>(new HBox(courseCheckBox), courseIcon);
            professionTreeItem.getChildren().add(courseTreeItem);
            courseCheckBoxList.add(courseCheckBox);
        }

        professionTreeItem.setExpanded(true);
        coursesVBox.getChildren().add(professionNode);
    }

    private void selectCourses(ActionEvent event){
        CheckBox selectedCheckBox = (CheckBox)event.getSource();
        String selectedCourseCode = selectedCheckBox.getText().substring(0, 2);
        List<Course> courseList = this.profession.getCourseList();

        for (Course course: courseList){
            if (selectedCourseCode.equals(course.getCode()))
                this.course = course;
        }

        for(CheckBox checkBox: this.courseCheckBoxList)
            checkBox.setSelected(false);

        selectedCheckBox.setSelected(true);
        this.bringQuestions();
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inTeacherEvent(TeacherEvent event) {
        Message message = event.getMessage();
        String eventType = event.getEventType();

        if (eventType.equals("Received")) {
            if (message.getItemsType().equals("Question") && message.isList()) {
                this.questionList = (List<Question>) message.getObjList();
            }
            if (message.getItemsType().equals("Grades") && message.isList()) {
                List<Grade> grades = (List<Grade>) message.getObjList();
                List<Integer> tempInt = new ArrayList<>();
                for (Grade grade: grades){
                    tempInt.add(grade.getGrade());
                }
                this.questionsPoints = tempInt;
            }
        }
        else if (eventType.equals("Created Exam")) {
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("messageWindow.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((messageWindowController) fxmlLoader.getController()).setMessage("A new exam was\n" +
                        "created and saved.");
                stage.setScene(scene);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.close();
            });
        }
        else if (eventType.equals("Updated Exam")) {
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("messageWindow.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ((messageWindowController) fxmlLoader.getController()).setMessage("The exam updated.");
                stage.setScene(scene);
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                stage.close();
            });
        }
        else if (eventType.equals("Pulled Exam")){
            Platform.runLater(() -> {
                FXMLLoader fxmlLoader = new FXMLLoader(TeacherApp.class.getResource("messageWindow.fxml"));
                Scene scene = null;
                try {
                    scene = new Scene(fxmlLoader.load());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(message.getItemsType().equals("PulledExam"))
                    ((messageWindowController) fxmlLoader.getController()).setMessage("The exam was pulled.");
                else if(message.getItemsType().equals("Error"))
                    ((messageWindowController) fxmlLoader.getController()).setMessage("The execution code \n" +
                            "is already in use.");
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
