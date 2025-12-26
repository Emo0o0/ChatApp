package com.example.rest.websocket;

import com.example.core.service.ChatMessageService;
import com.example.core.service.ChatSessionRegistry;
import com.example.persistence.entity.ChatRoomMember;
import io.quarkus.websockets.next.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@WebSocket(path = "/chat/{roomId}/{userId}")
public class ChatWebSocket {

    @Inject
    ChatSessionRegistry registry;
    @Inject
    ChatMessageService messageService;

    @OnOpen
    @Transactional
    public void onOpen(WebSocketConnection conn) {
        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String userId = conn.pathParam("userId");

        boolean isMember = ChatRoomMember.count(
                "chatRoom.id = ?1 AND chatUser.id = ?2",
                roomId, userId
        ) > 0;

        if (!isMember) {
            conn.close();
            return;
        }

        registry.add(roomId, userId, conn);
    }

    @OnTextMessage
    @Transactional
    public void onMessage(String message, WebSocketConnection conn) {

        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String userId = conn.pathParam("userId");

        boolean isMember = ChatRoomMember.count(
                "chatRoom.id = ?1 AND chatUser.id = ?2",
                roomId, userId
        ) > 0;

        if (!isMember) {
            conn.close();
            return;
        }

        messageService.saveAndBroadcast(roomId, userId, message, conn);
    }

    @OnClose
    public void onClose(WebSocketConnection conn) {
        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String userId = conn.pathParam("userId");
        registry.remove(roomId, userId);
    }
}
