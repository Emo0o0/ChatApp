package com.example.rest;

import com.example.core.service.ChatMessageService;
import com.example.persistence.entity.ChatMessage;
import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatUser;
import io.quarkus.websockets.next.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@WebSocket(path = "/chat/{roomId}/{username}")
public class ChatWebSocket {

    @Inject
    WebSocketConnection connection;
    @Inject
    ChatSessionRegistry registry;
    @Inject
    ChatMessageService messageService;

    @OnOpen
    public void onOpen(WebSocketConnection conn) {
        Long roomId = Long.valueOf(connection.pathParam("roomId"));
        String username = connection.pathParam("username");

        registry.add(roomId, username, conn);
    }

    @OnTextMessage
    @Transactional
    public void onMessage(String message, WebSocketConnection conn) {

        Long roomId = Long.valueOf(connection.pathParam("roomId"));
        String username = connection.pathParam("username");

        messageService.saveAndBroadcast(roomId, username, message, conn);
    }

    @OnClose
    public void onClose(WebSocketConnection conn) {
        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String username = conn.pathParam("username");
        registry.remove(roomId, username);
    }
}
