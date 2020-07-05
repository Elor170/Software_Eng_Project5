package org.example.Software_Eng_Project5.client.user.headmaster.GUI;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import org.example.Software_Eng_Project5.client.user.headmaster.HeadmasterEvent;
import org.example.Software_Eng_Project5.client.user.student.GUI.mainStudentController;
import org.example.Software_Eng_Project5.client.user.student.StudentApp;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.client.user.teacher.GUI.ExamWindowController;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherApp;
import org.example.Software_Eng_Project5.entities.*;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class mainHeadmasterController
{
    private boolean isShowQuestions;
    private boolean isShowExams;
    private boolean isShowFinishedExams;
//    private String professionCode;
//    private String courseCode;
    private Profession profession;
//    private Course course;
    private List<Profession> professionList;
    private List<Exam> examList;
    private List<Question> questionList;
    private List<SolvedExam> solvedExamList;
    private ArrayList<String> answersList;
    private List<TimeRequest> timeRequests;
    private int newTime;
    private int timeId;

    @FXML
    private BorderPane borderPane;
    @FXML
    private Label userNameLabel;
    @FXML
    private Button showQuestionsB;
    @FXML
    private javafx.scene.layout.VBox professionVBox;
    @FXML
    private VBox contentVBox;
    @FXML
    private Label contentTitle;
    @FXML
    private VBox VBox;
    @FXML
    private Button finishedExamsB;
    @FXML
    private Button timeRequestsB;

    public void setUserName(String userName){ userNameLabel.setText(userName); }

    @FXML
    private void showQuestions(ActionEvent event){
        this.isShowExams = false;
        this.isShowQuestions = true;
        this.isShowFinishedExams = false;
        this.contentVBox.getChildren().clear();
//        boolean isCourse = false;
//        boolean isProfession = false;
//        boolean isMyQuestions = false;
//        if (this.courseCode != null)
//            isCourse = true;
//        else if(this.professionCode != null)
//            isProfession = true;
//        else
//            isMyQuestions = true;
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("AllQuestions");

//        if (isCourse) {
//            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  "
//                    +  this.course.getCode() + "  " + this.course.getName() + ":  Questions" );
//            msg.setClassType(Course.class);
//            msg.setIndexString(this.courseCode);
//        }
//
//        else if(isProfession) {
//            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  Questions" );
//            msg.setClassType(Profession.class);
//            msg.setIndexString(this.professionCode);
//        }
//
//        else {
        this.contentTitle.setText("Questions");
        msg.setClassType(HeadMaster.class);
        msg.setIndexString(this.userNameLabel.getText());
  //      }

        EventBus.getDefault().post(new HeadmasterEvent(msg,"Bring"));
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

//                 if(isMyQuestions){
//                     Button editQuestionB = new Button("Edit Question");
//                     editQuestionB.setStyle("-fx-background-color: #DADCE0;" + "-fx-padding: 10 10 5 100;" +
//                             "-fx-border-insets: 5 5 0 90;" + "-fx-background-insets: 5 5 0 90");
//                     editQuestionB.setOnAction(this::onEditQuestion);
//                     questionVBox.getChildren().add(editQuestionB);
//                 }
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
//        if (! isMyQuestions){
//            Button addQuestion = new Button("Create Question");
//            addQuestion.setStyle("-fx-background-color: #DADCE0;" + "-fx-font-size: 16;");
//            addQuestion.setOnAction((this::onCreateQuestion));
//            HBox hBox = new HBox(addQuestion);
//            hBox.setAlignment(Pos.CENTER);
//            this.contentVBox.getChildren().add(hBox);
//        }
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
//      boolean isMyExams = false;
//        if (this.courseCode != null)
//            isCourse = true;
//        else if(this.professionCode != null)
//            isProfession = true;
//       else
//           isMyExams = true;
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("FinishedExams");

//        if (isCourse) {
//            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  "
//                    +  this.course.getCode() + "  " + this.course.getName() + ": Finished Exams" );
//            msg.setClassType(Course.class);
//            msg.setIndexString(this.courseCode);
//        }
//
//        else if(isProfession) {
//            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":Finished Exams" );
//            msg.setClassType(Profession.class);
//            msg.setIndexString(this.professionCode);
//        }

//        else {
        this.contentTitle.setText("Finished Exams");
        msg.setClassType(HeadMaster.class);
        msg.setIndexString(this.userNameLabel.getText());
//        }

        EventBus.getDefault().post(new HeadmasterEvent(msg,"Bring"));
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
                examTimeLabel = new Label(  "Time: " + hours + ":" + minutes);
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
//        String professionCode = selectedExam.getPulledExam().getOriginalExam().getCode().substring(0,2);
//        for (Profession profession: professionList){
//            if(profession.getCode().equals(professionCode))
//                this.profession = profession;
//        }
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
        Message msg = new Message();
        msg.setSingleObject(selectedExam.getPulledExam().getOriginalExam());
        msg.setList(true);
        msg.setItemsType("ExamProfession");
        msg.setClassType(HeadMaster.class);
        msg.setIndexString(this.userNameLabel.getText());

        EventBus.getDefault().post(new HeadmasterEvent(msg,"Bring"));
        this.profession = null;
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.profession == null);
        ExamWindowController examControllerWindow = fxmlLoader.getController();
        EventBus.getDefault().register(examControllerWindow);
        boolean isExamUsed = false;
        if(isEditExam)
            isExamUsed = selectedExam.getPulledExam().getOriginalExam().isPulled();
        String answers = answersList.get(solvedExamList.indexOf(selectedExam));
        examControllerWindow.setParameters(stage, this.profession, null, selectedExam,
                isExamUsed, isEditExam, isCreatExam, isShowSingleExam, isShowFinished, userNameLabel.getText(), answers, HeadMaster.class);
    }

    @FXML
    void showExams(ActionEvent event) {
        this.isShowExams = true;
        this.isShowQuestions = false;
        this.isShowFinishedExams = false;
        this.contentVBox.getChildren().clear();
//        boolean isCourse = false;
//        boolean isProfession = false;
//        boolean isMyExams = false;
//        if (this.courseCode != null)
//            isCourse = true;
//        else if(this.professionCode != null)
//            isProfession = true;
//        else
//            isMyExams = true;
        Message msg = new Message();
        msg.setList(true);
        msg.setItemsType("AllExams");

//        if (isCourse) {
//            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  "
//                    +  this.course.getCode() + "  " + this.course.getName() + ":  Exams" );
//            msg.setClassType(Course.class);
//            msg.setIndexString(this.courseCode);
//        }
//
//        else if(isProfession) {
//            this.contentTitle.setText(this.profession.getCode() + "  " + this.profession.getName() + ":  Exams" );
//            msg.setClassType(Profession.class);
//            msg.setIndexString(this.professionCode);
//        }
//
//        else {
        this.contentTitle.setText("Exams");
        msg.setClassType(HeadMaster.class);
        msg.setIndexString(this.userNameLabel.getText());
//        }

        EventBus.getDefault().post(new HeadmasterEvent(msg,"Bring"));
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
//                if(isMyExams){
//                    showOrEditExamB = new Button("Edit");
//                    showOrEditExamB.setOnAction(this::editExam);
//                }
//                else {
                showOrEditExamB = new Button("Show");
                showOrEditExamB.setOnAction(this::showSingleExam);
              //  }
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
        Message msg = new Message();
        msg.setSingleObject(selectedExam);
        msg.setList(true);
        msg.setItemsType("ExamProfession");
        msg.setClassType(HeadMaster.class);
        msg.setIndexString(this.userNameLabel.getText());

        EventBus.getDefault().post(new HeadmasterEvent(msg,"Bring"));
        this.profession = null;
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.profession == null);

        ExamWindowController examControllerWindow = fxmlLoader.getController();
        EventBus.getDefault().register(examControllerWindow);
        boolean isExamUsed = false;
        if(isEditExam)
            isExamUsed = selectedExam.isPulled();
        examControllerWindow.setParameters(stage, this.profession, selectedExam, null,
                isExamUsed, isEditExam, isCreatExam, isShowSingleExam, isShowFinished, userNameLabel.getText(), null, HeadMaster.class);
    }

//    public String getProfessionCode() {
//        return professionCode;
//    }
//
//    public void setProfessionCode(String professionCode) {
//        this.professionCode = professionCode;
//        this.courseCode = null;
//        for (Profession profession: professionList){
//            if(profession.getCode().equals(professionCode))
//                this.profession = profession;
//        }
//    }
//
//    public String getCourseCode() {
//        return courseCode;
//    }
//
//    public void setCourseCode(String courseCode) {
//        this.courseCode = courseCode;
//        List<Course> courses = this.profession.getCourseList();
//        for (Course course: courses){
//            if(course.getCode().equals(courseCode))
//                this.course = course;
//        }
//    }

    @FXML
    void showTimeRequests(ActionEvent event)
    {
        this.contentVBox.getChildren().clear();
        this.contentTitle.setText("Time Requests");
        Message message = new Message();
        message.setItemsType("TimeRequest");
        message.setList(true);
        EventBus.getDefault().post(new HeadmasterEvent(message, "GetRequests"));
        this.timeRequests = null;
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }while (this.timeRequests == null);

        if(!this.timeRequests.isEmpty()){
            Button ConfirmB;
            Button DeclineB;
            HBox examHBox;
            Label examCodeLabel;
            Label examTimeLabel;

            for (TimeRequest timeRequest : this.timeRequests){
                examHBox = new HBox();
//                if(isMyExams){
//                    showOrEditExamB = new Button("Edit");
//                    showOrEditExamB.setOnAction(this::editExam);
//                }
//                else {
                ConfirmB = new Button("Confirm");
                ConfirmB.setOnAction(this::ConfirmRequest);
                DeclineB = new Button("Decline");
                DeclineB.setOnAction(this::RemoveRequest);
                //  }
                ConfirmB.setStyle("-fx-background-color: #DADCE0;" + "-fx-background-insets: 5");
                DeclineB.setStyle("-fx-background-color: #DADCE0;" + "-fx-background-insets: 5");

                examCodeLabel = new Label("Execution Code: " + timeRequest.getExecCode());
                examCodeLabel.setStyle("-fx-text-fill: #d6e0e5;");
                newTime = timeRequest.getTime();
                timeId = timeRequest.getId();
                int hours = Math.floorDiv(timeRequest.getTime(),60);
                String minutes = Integer.toString(timeRequest.getTime() - 60 * hours);
                if(minutes.length() < 2)
                    minutes = "0" + minutes;
                examTimeLabel = new Label(  "New Time: " + hours + ":" + minutes);
                examTimeLabel.setStyle("-fx-text-fill: #d6e0e5;");

                examHBox.getChildren().addAll(ConfirmB, DeclineB, examCodeLabel, examTimeLabel);
                examHBox.setSpacing(20);
                examHBox.setStyle("-fx-font-size: 16;");
                this.contentVBox.getChildren().add(examHBox);
            }
        }

        // Case: No exams in the selected course/profession
        else {
            Label label = new Label("There are no requests yet.");
            label.setStyle("-fx-text-fill: #d6e0e5;" + "-fx-padding: 20 20 5 5;"
                    + "-fx-font-size: 16;");
            this.contentVBox.getChildren().add(label);
        }
    }

    private void ConfirmRequest(ActionEvent actionEvent)
    {
        String selectedExamCode = (((Label)((HBox)((Button)actionEvent.getSource()).getParent()).getChildren().get(2)).getText());

        StudentApp studentApp;
        Message message = new Message();
        message.setSingleObject(selectedExamCode.substring(16));
        message.setSingleObject2(newTime);

        EventBus.getDefault().post(new HeadmasterEvent(message, "Change Time"));
        RemoveRequest(actionEvent);
    }

    private void RemoveRequest(ActionEvent actionEvent)
    {
        String selectedExamCode = (((Label)((HBox)((Button)actionEvent.getSource()).getParent()).getChildren().get(2)).getText());

        Message message = new Message();
        message.setSingleObject(selectedExamCode.substring(16));
        message.setSingleObject2(newTime);
        message.setTestTime(timeId);
        EventBus.getDefault().post(new HeadmasterEvent(message, "Delete Time"));
    }

    public List<Profession> getProfessionList() {
        return professionList;
    }

    public void setProfessionList(List<Profession> professionList) {
        this.professionList = professionList;
    }

    @Subscribe
    @SuppressWarnings("unchecked")
    public void inHeadmasterEvent(HeadmasterEvent event){
        Message message = event.getMessage();
        String eventType = message.getItemsType();

        switch (eventType) {
            case "timeRequests":
                this.timeRequests = (List<TimeRequest>) message.getObjList();
                break;

            case "allQuestions":
                this.questionList = (List<Question>) message.getObjList();
                break;

            case "allExams":
                this.examList = (List<Exam>) message.getObjList();
                break;

            case "finishedExams":
                this.solvedExamList = (List<SolvedExam>) message.getObjList();
                this.answersList = (ArrayList<String>) message.getObjList2();
                break;

            case "examProfession":
                this.profession = (Profession) message.getSingleObject();
                break;

            case "Created Question":
            case "Updated Question":
                Platform.runLater(() -> this.showQuestions(null));
                break;
            case "Created Exam":
            case "Updated Exam":
           //     Platform.runLater(() -> this.showExams(null));
                break;
//            case "Updated Solved Exam":
//                Platform.runLater(() -> this.showFinishedExams(null));
//                break;
        }
    }
}
