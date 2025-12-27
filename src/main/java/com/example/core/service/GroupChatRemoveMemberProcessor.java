package com.example.core.service;

import com.example.api.inputoutput.chat_room.group.remove_member.GroupChatRemoveMemberOperation;
import com.example.api.inputoutput.chat_room.group.remove_member.GroupChatRemoveMemberRequest;
import com.example.api.inputoutput.chat_room.group.remove_member.GroupChatRemoveMemberResponse;
import com.example.core.exception.ChatRoomMemberNotFoundException;
import com.example.core.exception.ChatRoomNotFoundException;
import com.example.core.exception.ChatRoomRemoveException;
import com.example.core.exception.ChatUserNotFoundException;
import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatRoomMember;
import com.example.persistence.entity.ChatRoomType;
import com.example.persistence.entity.ChatUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;


@ApplicationScoped
public class GroupChatRemoveMemberProcessor implements GroupChatRemoveMemberOperation {

    @Inject
    ChatSessionRegistry registry;

    @Override
    @Transactional
    public GroupChatRemoveMemberResponse process(GroupChatRemoveMemberRequest request) {

        validate(request);

        ChatRoomMember member = ChatRoomMember
                .find("chatRoom.id = ?1 AND chatUser.id = ?2", request.roomId(), request.userId())
                .firstResult();
        member.delete();

        registry.remove(request.roomId(), request.userId().toString());

        return new GroupChatRemoveMemberResponse(true);
    }

    private void validate(GroupChatRemoveMemberRequest request) {
        ChatRoom room = ChatRoom.<ChatRoom>findByIdOptional(request.roomId())
                .orElseThrow(() -> new ChatRoomNotFoundException("Room with id [" + request.roomId() + "] was not found"));
        if (room.getType() == ChatRoomType.PRIVATE)
            throw new ChatRoomRemoveException("Cannot remove members from private chats");
        ChatUser user = ChatUser.<ChatUser>findByIdOptional(request.userId())
                .orElseThrow(() -> new ChatUserNotFoundException("User with id [" + request.userId() + "] was not found"));
        ChatRoomMember member = ChatRoomMember
                .<ChatRoomMember>find("chatRoom.id = ?1 AND chatUser.id = ?2", request.roomId(), request.userId())
                .firstResultOptional()
                .orElseThrow(() -> new ChatRoomMemberNotFoundException("ChatRoomMember with room id [" + request.roomId() + "] " +
                                                                       "and user id [" + request.userId() + "] was not found"));
    }
}
