package com.example.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

import java.time.Instant;

@Entity(name = "chat_room_members")
public class ChatRoomMember extends PanacheEntity {

    @ManyToOne
    private ChatRoom chatRoom;
    @ManyToOne
    private ChatUser chatUser;
    private Instant joinedAt;
    private boolean muted;

    public ChatRoomMember() {
    }

    public ChatRoomMember(ChatRoom room, ChatUser user) {
        this.chatRoom = room;
        this.chatUser = user;
    }

}
