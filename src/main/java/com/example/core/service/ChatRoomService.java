package com.example.core.service;

import com.example.api.inputoutput.chat_room.private_chat.get_or_create.GetOrCreatePrivateRoomOperation;
import com.example.api.inputoutput.chat_room.private_chat.get_or_create.GetOrCreatePrivateRoomRequest;
import com.example.api.inputoutput.chat_room.private_chat.get_or_create.GetOrCreatePrivateRoomResponse;
import com.example.core.exception.ChatUserNotFoundException;
import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatRoomMember;
import com.example.persistence.entity.ChatRoomType;
import com.example.persistence.entity.ChatUser;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChatRoomService implements GetOrCreatePrivateRoomOperation {

    @Override
    @Transactional
    public GetOrCreatePrivateRoomResponse process(GetOrCreatePrivateRoomRequest request) {
//        ChatUser me = ChatUserContext.getAuthenticatedUser(); ! change validation !
        validate(request);
        ChatUser me = ChatUser.find("id", request.user1Id()).firstResult();
        ChatUser other = ChatUser.find("id", request.user2Id()).firstResult();

        ChatRoom room = ChatRoom.find("""
                                SELECT r FROM chat_rooms r
                                JOIN chat_room_members m1 ON m1.chatRoom = r
                                JOIN chat_room_members m2 ON m2.chatRoom = r
                                WHERE r.type = :type
                                AND m1.chatUser = :user1
                                AND m2.chatUser = :user2
                                """,
                        Parameters.with("type", ChatRoomType.PRIVATE)
                                .and("user1", me)
                                .and("user2", other))
                .firstResult();

        if (room != null)
            return new GetOrCreatePrivateRoomResponse(room.id);

        room = new ChatRoom(ChatRoomType.PRIVATE);
        room.persist();
        persistMember(room, me);
        persistMember(room, other);

        return new GetOrCreatePrivateRoomResponse(room.id);
    }

    private void persistMember(ChatRoom room, ChatUser user) {
        ChatRoomMember m = new ChatRoomMember(room, user);
        m.persist();
    }

    private void validate(GetOrCreatePrivateRoomRequest request) {
        ChatUser user1 = ChatUser.<ChatUser>findByIdOptional(request.user1Id())
                .orElseThrow(() -> new ChatUserNotFoundException("User with id [" + request.user1Id() + "] was not found"));
        ChatUser user2 = ChatUser.<ChatUser>findByIdOptional(request.user2Id())
                .orElseThrow(() -> new ChatUserNotFoundException("User with id [" + request.user1Id() + "] was not found"));
    }
}
