package com.example.rest.websocket;

import com.example.core.service.ChatMessageService;
import com.example.core.service.ChatSessionRegistry;
import io.quarkus.websockets.next.*;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.antlr.v4.runtime.misc.Pair;

@WebSocket(path = "/chat/{roomId}/{userId}")
public class ChatWebSocket {

    @Inject
    ChatSessionRegistry registry;
    @Inject
    ChatMessageService messageService;

    @OnOpen
    public void onOpen(WebSocketConnection conn) {
        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String userId = conn.pathParam("userId");

        registry.add(roomId, userId, conn);
    }

    @OnTextMessage
    @Transactional
    public void onMessage(String message, WebSocketConnection conn) {

        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String userId = conn.pathParam("userId");

        messageService.saveAndBroadcast(roomId, userId, message, conn);
    }

    @OnClose
    public void onClose(WebSocketConnection conn) {
        Long roomId = Long.valueOf(conn.pathParam("roomId"));
        String userId = conn.pathParam("userId");
        registry.remove(roomId, userId);
    }
}
