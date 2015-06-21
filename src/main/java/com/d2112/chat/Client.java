package com.d2112.chat;

import com.d2112.connection.ClientConnection;

public class Client {
    private static int lastAssignedID = 0; //for increment
    private int id;
    private ClientConnection connection;
    private String name;
    private Integer currentRoomID;

    public Client(ClientConnection connection) {
        id = ++lastAssignedID;
        this.connection = connection;
        connection.setClientID(id);
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCurrentRoomID() {
        return currentRoomID;
    }

    public void setCurrentRoomID(Integer currentRoomID) {
        this.currentRoomID = currentRoomID;
    }

    public void sendMessage(String message) {
        connection.sendMessage(message);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (id != client.id) return false;
        if (connection != null ? !connection.equals(client.connection) : client.connection != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        return !(currentRoomID != null ? !currentRoomID.equals(client.currentRoomID) : client.currentRoomID != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (connection != null ? connection.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (currentRoomID != null ? currentRoomID.hashCode() : 0);
        return result;
    }
}
