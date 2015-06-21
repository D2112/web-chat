package com.d2112.chat;

import java.util.ArrayList;
import java.util.List;

public class Room {
    private static int lastAssignedID = 0; //for increment
    private int id;
    private Status status;
    private List<Client> members;

    public Room() {
        id = ++lastAssignedID;
        members = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void addMember(Client client) {
        members.add(client);
    }

    public void removeMember(Client client) {
        members.remove(client);
    }

    public List<Client> getMembers() {
        return members;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        BUSY, ENDED, FREE
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Room room = (Room) o;

        if (id != room.id) return false;
        if (status != room.status) return false;
        return !(members != null ? !members.equals(room.members) : room.members != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (members != null ? members.hashCode() : 0);
        return result;
    }
}
