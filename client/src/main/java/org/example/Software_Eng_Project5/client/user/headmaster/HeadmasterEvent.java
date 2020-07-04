package org.example.Software_Eng_Project5.client.user.headmaster;

import org.example.Software_Eng_Project5.client.user.UserEvent;
import org.example.Software_Eng_Project5.entities.Message;

public class HeadmasterEvent extends UserEvent
{
    public HeadmasterEvent(Message message, String eventType) {
        super(message, eventType);
    }
}
