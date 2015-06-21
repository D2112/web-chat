package com.d2112.connection;

public interface ConnectionHandler {

    void onOpen(ClientConnection connection);

    void onMessage(ClientConnection connection, String message);

    void onError(ClientConnection connection, Throwable throwable);

    void onClose(ClientConnection connection, String reason, Integer statusCode);

}
