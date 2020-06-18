package org.example.Software_Eng_Project5.client;

import org.example.Software_Eng_Project5.client.ocsf.AbstractClient;
import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class SimpleClient extends AbstractClient {
	
	private static org.example.Software_Eng_Project5.client.SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Message.class)) {
			if (((Message) msg).getCommand().equals("User Event"))
				EventBus.getDefault().post(new UserEvent((Message) msg, "LogIn check"));
		}

	}


	public static org.example.Software_Eng_Project5.client.SimpleClient getClient() {
		if (client == null) {
			client = new org.example.Software_Eng_Project5.client.SimpleClient("localhost", 3000);
		}
		return client;
	}

	private void bringOne(Message message) throws IOException {
		message.setCommand("Bring One");
		org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
	}

	private void bringList(Message message) throws IOException {
		message.setCommand("Bring List");
		org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
	}


	@Subscribe
	@SuppressWarnings("unchecked")
	public void inUserEvent (UserEvent event) throws IOException {
		Message message = event.getMessage();
		String eventType = event.getEventType();
		if(eventType.equals("LogIn")){
			message.setCommand("LogIn");
			org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
		}
		else if(eventType.equals("LogOut")){
			message.setCommand("LogOut");
			org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
		}
	}
}
