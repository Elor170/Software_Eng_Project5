

package org.example.Software_Eng_Project5.client;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.example.Software_Eng_Project5.entities.Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrimaryController {

	@FXML
	private TextField userNameTF;

	@FXML
	private TextField passwordTF;
	private static String password;

	@FXML
	private Button logInB;

	@FXML
	void logInB(ActionEvent event) {
		List<String> stringList = new ArrayList<>();
		String userName = userNameTF.getText();
		this.password = passwordTF.getText();
		Message message = new Message();
		message.setCommand("getObject");
		message.setIndexString(userName);
		message.setType("User");
		try {
			org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String getPassword() {
		return password;
	}
}


