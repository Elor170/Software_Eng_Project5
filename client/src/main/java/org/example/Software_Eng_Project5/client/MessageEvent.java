package org.example.Software_Eng_Project5.client;

import org.example.Software_Eng_Project5.entities.Message;

public class MessageEvent {
    private Message message;

    public Message getMessage() {
        return message;
    }

    public MessageEvent(Message message) {
        this.message = message;
    }
}