package com.example.api.inputoutput.chat_room.group.remove_member;

import com.example.api.base.OperationRequest;

public record GroupChatRemoveMemberRequest(Long roomId, Long userId) implements OperationRequest {
}
