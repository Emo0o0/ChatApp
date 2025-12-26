package com.example.core.service;

import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberOperation;
import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberRequest;
import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberResponse;
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
        ChatRoom room = ChatRoom.findById(request.roomId());
        if (room.getType() != ChatRoomType.GROUP)
            throw new ChatRoomMemberException("Cannot add members to private chat", 400);

        ChatUser user = ChatUser.find("id", request.userId()).firstResult();
        if (user == null)
            throw new ChatUserNotFoundException("Chat user with id [" + request.userId() + "] was not found");

        ChatRoomMember member = new ChatRoomMember(room, user);
        member.persist();
        return new GroupChatAddMemberResponse(true);
    }
}
