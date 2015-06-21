package com.d2112.connection;

public interface ClientConnection {

    void sendMessage(String message);

    Integer getClientID();

    void setClientID(int ID);

    Object getAttribute(String name);

    Object setAttribute(String name, Object value);

    void close();
}
