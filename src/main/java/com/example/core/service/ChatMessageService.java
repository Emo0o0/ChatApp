package com.example.core.service;

import com.example.persistence.entity.ChatMessage;
import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatUser;
import com.example.rest.ChatSessionRegistry;
import io.quarkus.websockets.next.WebSocketConnection;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

import java.util.Collection;

@ApplicationScoped
public class ChatMessageService {

    @Inject
    ChatSessionRegistry registry;

    @Transactional
    public void saveAndBroadcast(Long roomId, String username, String message, WebSocketConnection connection) {
        ChatRoom room = ChatRoom.findById(roomId);
        ChatUser sender = ChatUser.find("username", username).firstResult();

        ChatMessage cm = new ChatMessage(room, sender, message);
        cm.persist();

        for (WebSocketConnection conn : registry.othersInRoom(roomId, connection)) {
            conn.sendTextAndAwait(username + ": " + message);
        }
    }

}
