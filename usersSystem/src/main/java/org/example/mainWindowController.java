package org.example;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.Software_Eng_Project5.entities.Profession;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class mainWindowController {

    private static List<Profession> professionsList;
    private static boolean listCheck = false;
    private List<Profession> teacherProfessions = null;

    @FXML
    private TextField userNameTextField;

    @FXML
    private TextField passwordTextField;

    @FXML
    private TextField identTextField;

    @FXML
    private ChoiceBox<String> userTypeChoiceBox;

    @FXML
    private Button addUserButton;

    @FXML
    private VBox professionsVBox;

    public static void setProfessionsList(List<Profession> all) {
        professionsList = all;
    }

    @FXML
    void addUser(ActionEvent event) {
        String userName = userNameTextField.getText();
        String password = passwordTextField.getText();
        String userType = userTypeChoiceBox.getValue();
        String identification = identTextField.getText();
//      showIdentificationField(userType);
//        if(identTextField.isVisible())
//            identification = identTextField.getText();
        App.addUser2DB(userName, password, userType, teacherProfessions, identification);
    }

//    @FXML
//    public void showIdentificationField(String userType)
//    {
//        if(userType.equals("Student"))
//        {
//            identTextField.setVisible(true);
//        }
//        else
//            identTextField.setVisible(false);
//    }

    public void showProfessions(MouseEvent event) {
        if(!listCheck){
            listCheck = true;
            HBox hBox;
            Label codeLabel;
            Label nameLabel;
            CheckBox checkBox;
            for (Profession profession: professionsList){
                hBox = new HBox();
                codeLabel = new Label(profession.getCode());
                nameLabel = new Label("  " + profession.getName());
                checkBox = new CheckBox();
                CheckBox finalCheckBox = checkBox;
                checkBox.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        setTeacherProfessions(event , finalCheckBox.isSelected() , profession.getCode());
                    }
                });
                hBox.getChildren().addAll(checkBox, codeLabel, nameLabel);
                professionsVBox.getChildren().add(hBox);
            }
        }
    }

    @FXML
    void setTeacherProfessions(ActionEvent event, Boolean isSelected,String code) {
        System.out.println(code + ": " + isSelected + "\n");
        for(Profession profession: professionsList){
            if (profession.getCode().equals(code)) {
                if (teacherProfessions == null){
                    teacherProfessions = new ArrayList<Profession>();
                }

                if (isSelected){
                    this.teacherProfessions.add(profession);
                }
                else {
                    this.teacherProfessions.remove(profession);
                }
            }
        }
        for(Profession profession: teacherProfessions){
            System.out.println(profession.getCode() + ": " + profession.getName());
        }
    }
}

