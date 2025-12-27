package com.example.core.service;

import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberOperation;
import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberRequest;
import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberResponse;
import com.example.core.exception.ChatRoomAddException;
import com.example.core.exception.ChatRoomMemberException;
import com.example.core.exception.ChatUserNotFoundException;
import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatRoomMember;
import com.example.persistence.entity.ChatRoomType;
import com.example.persistence.entity.ChatUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class GroupChatAddMemberProcessor implements GroupChatAddMemberOperation {
    @Override
    @Transactional
    public GroupChatAddMemberResponse process(GroupChatAddMemberRequest request) {
        validate(request);

        ChatRoom room = ChatRoom.findById(request.roomId());

        ChatUser user = ChatUser.find("id", request.userId()).firstResult();

        ChatRoomMember member = new ChatRoomMember(room, user);
        member.persist();
        return new GroupChatAddMemberResponse(true);
    }

    private void validate(GroupChatAddMemberRequest request) {
        ChatRoom room = ChatRoom.<ChatRoom>findByIdOptional(request.roomId())
                .orElseThrow(() -> new ChatRoomMemberException("Room with id [" + request.roomId() + "] was not found", 400));

        if (room.getType() == ChatRoomType.PRIVATE)
            throw new ChatRoomAddException("Cannot add members to private chat");

        ChatUser user = ChatUser.<ChatUser>find("id", request.userId()).firstResultOptional()
                .orElseThrow(() -> new ChatUserNotFoundException("Chat user with id [" + request.userId() + "] was not found"));
    }
}
