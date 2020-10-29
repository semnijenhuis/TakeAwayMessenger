package com.springboks.takeawaymessenger.model;

import java.time.LocalDate;

public class Message {

    private int senderId;
    private User receiver;
    private LocalDate time;
    private String body;
    private Boolean isMe;
    private int orderId;

    public Message() {
    }

    public Message(String body, Boolean isMe) {
        this.body = body;
        this.isMe = isMe;
    }

    public Message(int senderId, String body, int orderId) {
        this.senderId = senderId;
        time = LocalDate.now();
        this.body = body;
        this.orderId = orderId;
    }

    public int getSenderId() {
        return senderId;
    }


    public String getBody() {
        return body;
    }


    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId=" + senderId +
                ", receiver=" + receiver +
                ", time=" + time +
                ", body='" + body + '\'' +
                ", isMe=" + isMe +
                ", orderId=" + orderId +
                '}';
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }
}
