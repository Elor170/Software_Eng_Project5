package org.example.Software_Eng_Project5.client.user;

import org.example.Software_Eng_Project5.entities.Message;

public class UserEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public UserEvent(Message message) {
        this.message = message;
    }
}