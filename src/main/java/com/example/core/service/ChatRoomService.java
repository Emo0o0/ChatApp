package com.example.core.service;

import com.example.persistence.entity.ChatRoom;
import com.example.persistence.entity.ChatRoomMember;
import com.example.persistence.entity.ChatRoomType;
import com.example.persistence.entity.ChatUser;
import io.quarkus.panache.common.Parameters;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ChatRoomService {

    @Transactional
    public ChatRoom getOrCreatePrivateRoom(ChatUser user1, ChatUser user2) {

        ChatRoom room = ChatRoom.find("""
                                SELECT r FROM chat_rooms r
                                JOIN chat_room_members m1 ON m1.chatRoom = r
                                JOIN chat_room_members m2 ON m2.chatRoom = r
                                WHERE r.type = :type
                                AND m1.chatUser = :user1
                                AND m2.chatUser = :user2
                                """,
                        Parameters.with("type", ChatRoomType.PRIVATE)
                                .and("user1", user1)
                                .and("user2", user2))
                .firstResult();

        if (room != null)
            return room;

        room = new ChatRoom(ChatRoomType.PRIVATE);
        room.persist();
        persistMember(room, user1);
        persistMember(room, user2);

        return room;
    }

    private void persistMember(ChatRoom room, ChatUser user) {
        ChatRoomMember m = new ChatRoomMember(room, user);
        m.persist();
    }
}
