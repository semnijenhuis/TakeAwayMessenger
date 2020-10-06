package com.springboks.takeawaymessenger.model;

import java.time.LocalDate;

public class Message {

    private User sender;
    private User receiver;
    private LocalDate time;
    private String body;
    private Boolean isMe;

    public Message(String body, Boolean isMe) {
        this.body = body;
        this.isMe = isMe;
    }

    public Message(User sender, User receiver, String body) {
        this.sender = sender;
        this.receiver = receiver;
        time = LocalDate.now();
        this.body = body;
    }

    public User getSender() {
        return sender;
    }

    public void setSender(User sender) {
        this.sender = sender;
    }

    public User getReceiver() {
        return receiver;
    }

    public void setReceiver(User receiver) {
        this.receiver = receiver;
    }

    public LocalDate getTime() {
        return time;
    }

    public void setTime(LocalDate time) {
        this.time = time;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }
}
