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
		List<String> stringList = new ArrayList<>();
		stringList.add(userNameTF.getText());
		stringList.add(passwordTF.getText());
		Message message = new Message();
		message.setObjList(stringList);
		EventBus.getDefault().post(new UserEvent(message, "LogIn"));
	}

	public void logInFailed(){
		status.setText("The user name or the password are wrong");
	}

}


