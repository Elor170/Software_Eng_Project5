package org.example.Software_Eng_Project5.server;

import org.example.Software_Eng_Project5.entities.Message;
import org.example.Software_Eng_Project5.server.ocsf.AbstractServer;
import org.example.Software_Eng_Project5.server.ocsf.ConnectionToClient;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class SimpleServer extends AbstractServer {

	public SimpleServer(int port) {
		super(port);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	protected void handleMessageFromClient(Object msg, ConnectionToClient client) {
		Message message = (Message) msg;
		if (message.getCommand().equals("#login")) {
			boolean checkUserName = ((List<String>)(message.getObjList())).get(0).equals("user");
			boolean checkPassword = ((List<String>)(message.getObjList())).get(1).equals("password");
			message.setObjList(null);
			if (checkPassword && checkUserName)
				message.setCommand("#login-Y");
			else
				message.setCommand("#login-N");

			try {
				client.sendToClient(message);
				System.out.format("Sent message to client %s\n", Objects.requireNonNull(client.getInetAddress()).getHostAddress());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
