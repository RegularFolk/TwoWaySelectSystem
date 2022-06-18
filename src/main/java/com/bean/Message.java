package com.bean;

public class Message {
    private int senderId;
    private int receiverId;
    private String text;
    private String time;

    public Message() {
    }

    public Message(int senderId, int receiverId, String text, String time) {
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.text = text;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Message{" +
                "senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public int getSenderId() {
        return senderId;
    }

    public void setSenderId(int senderId) {
        this.senderId = senderId;
    }

    public int getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(int receiverId) {
        this.receiverId = receiverId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
