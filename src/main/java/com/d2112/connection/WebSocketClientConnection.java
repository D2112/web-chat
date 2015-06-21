package com.d2112.connection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

public class WebSocketClientConnection implements ClientConnection {
    private static final Logger log = LoggerFactory.getLogger(WebSocketClientConnection.class);
    private static final String CLIENT_ID_ATTRIBUTE_NAME = "client_id";

    private WebSocketSession socketSession;

    public WebSocketClientConnection(WebSocketSession socketSession) {
        this.socketSession = socketSession;
    }

    @Override
    public void sendMessage(String message) {
        try {
            socketSession.sendMessage(new TextMessage(message));
        } catch (IOException e) {
            log.error("Error sending message", e);
        }
    }

    @Override
    public Integer getClientID() {
        return (Integer) socketSession.getAttributes().get(CLIENT_ID_ATTRIBUTE_NAME);
    }

    @Override
    public void setClientID(int id) {
        socketSession.getAttributes().put(CLIENT_ID_ATTRIBUTE_NAME, id);
    }

    @Override
    public Object getAttribute(String name) {
        return socketSession.getAttributes().get(name);
    }

    @Override
    public Object setAttribute(String name, Object value) {
        return socketSession.getAttributes().put(name, value);
    }

    @Override
    public void close() {
        try {
            socketSession.close();
        } catch (IOException e) {
            log.error("Error on close connection");
        }
    }

}
