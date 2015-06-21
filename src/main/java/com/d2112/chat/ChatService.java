package com.d2112.chat;

import com.d2112.connection.ClientConnection;
import com.d2112.connection.ConnectionHandler;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class ChatService implements ConnectionHandler {
    private static final Logger log = LoggerFactory.getLogger(ChatService.class);
    private static final int MAXIMUM_ROOM_USERS = 3;
    private static Gson gson = new Gson();
    private Map<Integer, Client> clientByIdMap;
    private Map<Integer, Room> roomByIdMap;

    public ChatService() {
        log.debug("Creating of " + ChatService.class.getName());
        clientByIdMap = new HashMap<>();
        roomByIdMap = new HashMap<>();
    }

    @Override
    public void onOpen(ClientConnection connection) {
        Client client = new Client(connection);
        clientByIdMap.put(client.getId(), client);
        addClientToFreeRoom(client);
    }

    @Override
    public void onMessage(ClientConnection connection, String messageText) {
        Client client = clientByIdMap.get(connection.getClientID());
        Message message = gson.fromJson(messageText, Message.class);
        handleMessage(message, client);
    }

    @Override
    public void onError(ClientConnection connection, Throwable throwable) {
        log.error("Error, client id: " + connection.getClientID(), throwable);
    }

    @Override
    public void onClose(ClientConnection connection, String reason, Integer statusCode) {
        log.debug("Connection closed, reason: " + reason + ", code: " + statusCode);
        Client client = findClient(connection);
        Room clientRoom = findClientRoom(client);
        clientRoom.removeMember(client);
        sendNotice(clientRoom, new Notice(Notice.CLIENT_LEFT, client.getName()));
        redefineRoomStatus(clientRoom);
    }

    private Client findClient(ClientConnection connection) {
        return clientByIdMap.get(connection.getClientID());
    }

    private Room findClientRoom(Client client) {
        return roomByIdMap.get(client.getCurrentRoomID());
    }

    private void handleMessage(Message message, Client client) {
        Message.Type type = message.getType();
        Room clientRoom = findClientRoom(client);
        switch (type) {
            case NAME:
                client.setName(message.getText());
                sendNotice(
                        clientRoom,
                        new Notice(Notice.CLIENT_JOINED, client.getName())
                );
                break;
            case MESSAGE:
                message = new Message(client.getName(), message.getText(), Message.Type.MESSAGE);
                broadcastMessageToRoom(clientRoom, message); //send message to the room
                break;
        }
    }

    private void sendNotice(Room room, Notice notice) {
        broadcastMessageToRoom(room, notice);
    }

    private void broadcastMessageToRoom(Room roomForMessaging, Message message) {
        String jsonMessage = convertMessageToJson(message);
        for (Client client : roomForMessaging.getMembers()) {
            client.sendMessage(jsonMessage);
        }
    }

    private void addClientToFreeRoom(Client client) {
        Room freeRoom = getFreeRoom();
        freeRoom.addMember(client);
        log.debug("Client added to room with ID: " + freeRoom.getId());
        client.setCurrentRoomID(freeRoom.getId()); //todo: does client need room id?
        redefineRoomStatus(freeRoom);
    }

    private String convertMessageToJson(Message msg) {
        return gson.toJson(msg);
    }

    private void redefineRoomStatus(Room room) {
        if (room.getMembers().size() >= MAXIMUM_ROOM_USERS) {
            room.setStatus(Room.Status.BUSY);
        } else {
            room.setStatus(Room.Status.FREE);
        }
        log.debug("Room " + room.getId() + " got status " + room.getStatus());
    }

    private Room getFreeRoom() {
        for (Map.Entry<Integer, Room> entry : roomByIdMap.entrySet()) {
            Room room = entry.getValue();
            if (room.getStatus() == Room.Status.FREE) {
                return room;
            }
        }
        Room freeRoom = new Room();
        roomByIdMap.put(freeRoom.getId(), freeRoom);
        return freeRoom;
    }
}
