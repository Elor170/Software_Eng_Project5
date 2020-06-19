package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Software_Eng_Project5.entities.Course;
import org.example.Software_Eng_Project5.entities.Profession;

import java.io.IOException;
import java.util.List;

public class addCourseWindowController {

    private static List<Profession> professionsList;
    private Profession lastChosenProfession;

    @FXML
    private TextField courseNameTF;

    @FXML
    private TextField courseCodeTF;

    @FXML
    private Label professionName;

    @FXML
    private Label professionCode;

    @FXML
    private Button returnB;

    @FXML
    private Button addB;

    @FXML
    private Button showProfessions;

    @FXML
    private VBox professionsVBox;


    @FXML
    void add2DB(ActionEvent event) {
        String courseName = courseNameTF.getText();
        String courseCode = courseCodeTF.getText();
        System.out.println("----" + this.lastChosenProfession.getName() + "----");
        Course course = new Course(courseCode, courseName, this.lastChosenProfession);
        App.addObject2DB(course);
    }

    @FXML
    void return2Main(ActionEvent event) {
        try {
            App.setRoot("mainWindow");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Profession> getProfessionsList() {
        return professionsList;
    }

    public static void setProfessionsList(List<Profession> professionsList) {
        addCourseWindowController.professionsList = professionsList;
    }

    @FXML
    void showProfessions(ActionEvent event) {
        HBox hBox;
        Label codeLabel;
        Label nameLabel;
        for (Profession profession: professionsList){
            hBox = new HBox();
            codeLabel = new Label(profession.getCode());
            nameLabel = new Label("  " + profession.getName());
            Label finalCodeLabel = codeLabel;
            Label finalNameLabel = nameLabel;
            nameLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    setProfessionName(mouseEvent, finalNameLabel.getText(),finalCodeLabel.getText());
                }
            });
            hBox.getChildren().addAll(codeLabel, nameLabel);
            professionsVBox.getChildren().add(hBox);
        }
    }

    @FXML
    void setProfessionName(MouseEvent event, String name,String code) {
        professionName.setText(name);
        professionCode.setText(code);
        for(Profession profession: professionsList){
            if (profession.getCode().equals(code)) {
                System.out.println("----" + profession.getName() + "----");
                this.lastChosenProfession = profession;
            }
        }
    }
}


