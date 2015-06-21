package com.d2112.chat;

public class Message {
    private String senderName;
    private String text;
    private Type type;

    public Message() {
    }

    public Message(String text, Type type) {
        this.text = text;
        this.type = type;
    }

    public Message(String senderName, String text, Type type) {
        this.senderName = senderName;
        this.text = text;
        this.type = type;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public enum Type {
        MESSAGE, NOTICE, NAME
    }
}
