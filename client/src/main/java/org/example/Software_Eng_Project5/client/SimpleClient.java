package org.example.Software_Eng_Project5.client;

import org.example.Software_Eng_Project5.client.ocsf.AbstractClient;
import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.client.user.headmaster.HeadmasterEvent;
import org.example.Software_Eng_Project5.client.user.student.StudentEvent;
import org.example.Software_Eng_Project5.client.user.teacher.TeacherEvent;
import org.example.Software_Eng_Project5.entities.Message;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import java.io.IOException;

public class SimpleClient extends AbstractClient {
	
	private static org.example.Software_Eng_Project5.client.SimpleClient client = null;

	private SimpleClient(String host, int port) {
		super(host, port);
	}

	public static org.example.Software_Eng_Project5.client.SimpleClient getClient() {
		if (client == null) {
			client = new org.example.Software_Eng_Project5.client.SimpleClient("localhost", 3000);
		}
		return client;
	}

	@Override
	protected void handleMessageFromServer(Object msg) {
		if (msg.getClass().equals(Message.class)) {
			Message message = (Message) msg;
			String command = message.getCommand();
			String eventType = message.getType();

			switch (command) {
				case "User Event":
					EventBus.getDefault().post(new UserEvent(message, "LogIn check"));
					break;
				case "Teacher Event":
					switch (eventType)
					{
						case "Received":
							EventBus.getDefault().post(new TeacherEvent(message, "Received"));
							break;
						case "Created Question":
							EventBus.getDefault().post(new TeacherEvent(message, "Created Question"));
							break;
						case "Created Exam":
							EventBus.getDefault().post(new TeacherEvent(message, "Created Exam"));
							break;
						case "Updated Question":
							EventBus.getDefault().post(new TeacherEvent(message, "Updated Question"));
							break;
						case "Updated Exam":
							EventBus.getDefault().post(new TeacherEvent(message, "Updated Exam"));
							break;
						case "Updated Solved Exam":
							EventBus.getDefault().post(new TeacherEvent(message, "Updated Solved Exam"));
							break;
						case "Pulled Exam":
							EventBus.getDefault().post(new TeacherEvent(message, "Pulled Exam"));
							break;
						case "Finished Exam":
							EventBus.getDefault().post(new TeacherEvent(message, "Finished Exam"));
							break;
						case "Time Request":
							EventBus.getDefault().post(new TeacherEvent(message, "Requested Time"));
					}
					break;

				case "Student Event":
					if(eventType.equals("Solved Exam"))
					{
						EventBus.getDefault().post(new StudentEvent(message, "Solved Exam"));
						//EventBus.getDefault().post(new TeacherEvent(message, "Solved Exam"));
					}
					else if(eventType.equals("Start Exam"))
						EventBus.getDefault().post(new StudentEvent(message, "Start Exam"));
					else if(eventType.equals("Solved Exams"))
					{
						EventBus.getDefault().post(new StudentEvent(message, "Solved Exams"));
					}
					break;

				case "Headmaster Event":
					if(eventType.equals("Received"))
						EventBus.getDefault().post(new HeadmasterEvent(message, "Received"));
					break;

			}
		}

	}


	@Subscribe
	public void inUserEvent (UserEvent event) throws IOException {
		Message message = event.getMessage();
		String eventType = event.getEventType();
		if(eventType.startsWith("Log")){
			if (eventType.endsWith("In"))
				message.setCommand("LogIn");
			else if(eventType.endsWith("Out")){
				message.setCommand("LogOut");
			}
			org.example.Software_Eng_Project5.client.SimpleClient.getClient().sendToServer(message);
		}
	}

	@Subscribe
	public void inTeacherEvent (TeacherEvent event)
	{

	}
}
