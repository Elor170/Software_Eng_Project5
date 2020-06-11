package org.example.Software_Eng_Project5.client;

import org.example.Software_Eng_Project5.client.ocsf.AbstractClient;
import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.EventBus;

public class SimpleClient extends AbstractClient {
	
	private static org.example.Software_Eng_Project5.client.SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Message.class)) {
			EventBus.getDefault().post(new UserEvent((Message) msg));
		}

	}
	
	public static org.example.Software_Eng_Project5.client.SimpleClient getClient() {
		if (client == null) {
			client = new org.example.Software_Eng_Project5.client.SimpleClient("localhost", 3000);
		}
		return client;
	}

}
