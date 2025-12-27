package com.example.api.inputoutput.chat_room.private_chat.get_or_create;

import com.example.api.base.OperationRequest;

public record GetOrCreatePrivateRoomRequest(Long user1Id, Long user2Id) implements OperationRequest {
}
