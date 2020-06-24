package org.example.Software_Eng_Project5.client.user.student;

import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.entities.Message;

public class StudentEvent extends UserEvent
{
    public StudentEvent(Message message, String eventType) {
        super(message, eventType);
    }
}
