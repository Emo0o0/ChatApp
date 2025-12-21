package com.example.core.service;

import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@ApplicationScoped
public class ChatSessionRegistry {

    private final Map<Long, Map<String, WebSocketConnection>> rooms = new ConcurrentHashMap<>();

    public void add(Long roomId, String username, WebSocketConnection connection) {
        rooms
                .computeIfAbsent(roomId, r -> new ConcurrentHashMap<>())
                .put(username, connection);
        System.out.println("ROOM " + roomId + " connections: " + rooms.get(roomId).keySet());
    }

    public void remove(Long roomId, String username) {
        Map<String, WebSocketConnection> room = rooms.get(roomId);
        if (room != null) {
            room.remove(username);
            if (room.isEmpty()) {
                rooms.remove(roomId);
            }
        }
    }

    public Collection<WebSocketConnection> othersInRoom(Long roomId, WebSocketConnection senderConn) {
        var others = rooms.getOrDefault(roomId, Map.of())
                .values()
                .stream()
                .filter(conn -> conn != senderConn)
                .toList();
        System.out.println("Sending to " + others.size() + " users");
        return others;
    }
}
