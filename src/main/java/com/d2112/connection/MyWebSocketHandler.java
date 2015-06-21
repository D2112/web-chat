package com.d2112.connection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

@Service
public class MyWebSocketHandler implements WebSocketHandler {
    @Autowired
    private ConnectionHandler connectionHandler;

    public MyWebSocketHandler() {
        System.out.println("Socket handler creating");
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        connectionHandler.onOpen(new WebSocketClientConnection(webSocketSession));
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        connectionHandler.onMessage(new WebSocketClientConnection(webSocketSession), webSocketMessage.getPayload().toString());
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        connectionHandler.onError(new WebSocketClientConnection(webSocketSession), throwable);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        connectionHandler.onClose(
                new WebSocketClientConnection(webSocketSession),
                closeStatus.getReason(),
                closeStatus.getCode()
        );
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }
}
