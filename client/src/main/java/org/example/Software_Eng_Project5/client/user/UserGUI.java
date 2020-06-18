package org.example.Software_Eng_Project5.client.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class UserGUI {

	@FXML
	private Label status;

	@FXML
	private TextField userNameTF;

	@FXML
	private TextField passwordTF;

	@FXML
	private Button logInB;


	@FXML
	private void logInB(ActionEvent event) {
		status.setText("");
		Message message = new Message();
		message.setIndexString(userNameTF.getText());
		message.setSingleObject(passwordTF.getText());
		EventBus.getDefault().post(new UserEvent(message, "LogIn"));
	}

	public void logInFailed(){
		status.setText("The user name or the password are wrong");
	}

	public void alreadyConnected(){
		status.setText("The user is already connected");
	}

}


