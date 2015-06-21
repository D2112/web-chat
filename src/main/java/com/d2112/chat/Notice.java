package com.d2112.chat;


public class Notice extends Message {
    public static final String CLIENT_JOINED = "joined to room.";
    public static final String CLIENT_LEFT = "left room.";
    public static final String CHAT_CLOSED = "all members left the room";

    public Notice(String text) {
        super(text, Type.NOTICE);
    }

    public Notice(String text, String clientName) {
        super(clientName + " " + text, Type.NOTICE);
    }
}


