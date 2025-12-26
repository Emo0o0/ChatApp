package com.example.api.inputoutput.chat_room.group.add_member;

import com.example.api.base.OperationRequest;

public record GroupChatAddMemberRequest(Long roomId, Long userId) implements OperationRequest {
}
