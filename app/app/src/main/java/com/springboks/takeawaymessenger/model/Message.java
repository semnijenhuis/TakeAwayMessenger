package com.springboks.takeawaymessenger.model;

import java.time.LocalDate;

public class Message {

    private User sender;
    private User receiver;
    private LocalDate time;
    private String body;

    public Message(User sender, User receiver, String body) {
        this.sender = sender;
        this.receiver = receiver;
        time = LocalDate.now();
        this.body = body;
    }
}
