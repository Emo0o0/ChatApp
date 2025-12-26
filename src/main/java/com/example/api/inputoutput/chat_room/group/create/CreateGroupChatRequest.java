package com.example.api.inputoutput.chat_room.group.create;

import com.example.api.base.OperationRequest;

import java.util.List;

public record CreateGroupChatRequest(String name, List<Long> members) implements OperationRequest {
}
