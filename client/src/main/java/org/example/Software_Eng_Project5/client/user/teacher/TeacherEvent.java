package org.example.Software_Eng_Project5.client.user.teacher;

import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.entities.Message;

public class TeacherEvent extends UserEvent {

    public TeacherEvent(Message message, String eventType) {
        super(message, eventType);
    }

}