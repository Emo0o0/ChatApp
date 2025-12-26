package com.example.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity(name = "chat_rooms")
public class ChatRoom extends PanacheEntity {

    private String name;
    @Enumerated(EnumType.STRING)
    private ChatRoomType type;
    @OneToMany(mappedBy = "room")
    private List<ChatMessage> messages;

    public ChatRoom() {

    }

    public ChatRoom(ChatRoomType type) {
        this.type = type;
    }
}
