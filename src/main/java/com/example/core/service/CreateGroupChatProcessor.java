package com.example.core.service;

import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatOperation;
import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatRequest;
import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatResponse;
import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatRoomMember;
import com.example.persistence.entity.ChatRoomType;
import com.example.persistence.entity.ChatUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class CreateGroupChatProcessor implements CreateGroupChatOperation {
    @Override
    @Transactional
    public CreateGroupChatResponse process(CreateGroupChatRequest request) {
        ChatRoom room = new ChatRoom(ChatRoomType.GROUP);
        room.setName(request.name());
        room.persist();
        for (Long userId : request.members()) {
            ChatUser user = ChatUser.find("id", userId).firstResult();
            ChatRoomMember roomMember = new ChatRoomMember(room, user);
            roomMember.persist();
        }
        return new CreateGroupChatResponse(room.id);
    }
}
