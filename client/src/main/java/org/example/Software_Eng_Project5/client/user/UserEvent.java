package org.example.Software_Eng_Project5.client.user;

import org.example.Software_Eng_Project5.entities.Message;

public class UserEvent {
    private Message message;
    private String eventType;

    public Message getMessage() {
        return message;
    }

    public String getEventType() {
        return eventType;
    }

    public UserEvent(Message message, String eventType) {
        this.message = message;
        this.eventType = eventType;
    }

}