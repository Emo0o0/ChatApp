package com.example.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity(name = "messages")
public class ChatMessage extends PanacheEntity {


    @ManyToOne(optional = false)
    private ChatUser sender;
    @ManyToOne(optional = false)
    private ChatRoom room;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    private Instant timestamp;

    public ChatMessage() {
    }

    public ChatMessage(ChatRoom room,ChatUser sender, String content) {
        this.room=room;
        this.sender = sender;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }

    public ChatUser getSender() {
        return this.sender;
    }
}
