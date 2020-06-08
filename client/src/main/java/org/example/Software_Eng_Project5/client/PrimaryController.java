

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

	@FXML
	private Button logInB;

	@FXML
	void logInB(ActionEvent event) {
		List<String> stringList = new ArrayList<>();
		stringList.add(userNameTF.getText());
		stringList.add(passwordTF.getText());
		Message message = new Message("#login", stringList);
		try {
			org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


