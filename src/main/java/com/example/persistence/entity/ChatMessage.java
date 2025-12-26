package com.example.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import io.quarkus.panache.common.Page;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.List;

@Entity(name = "messages")
@Table(indexes = {
        @Index(name = "idx_room_id_id", columnList = "room_id, id")
})
public class ChatMessage extends PanacheEntity {

    @ManyToOne(optional = false)
    private ChatUser sender;
    @ManyToOne(optional = false)
    private ChatRoom room;
    @Column(nullable = false)
    private String content;
    @CreationTimestamp
    private Instant timestamp;
    @Enumerated(EnumType.STRING)
    private ChatMessageStatus status;

    public ChatMessage() {
    }

    public ChatMessage(ChatRoom room, ChatUser sender, String content) {
        this.room = room;
        this.sender = sender;
        this.content = content;
        this.status = ChatMessageStatus.SENT;
    }

    public String getContent() {
        return this.content;
    }

    public ChatUser getSender() {
        return this.sender;
    }

    public Instant getTimestamp() {
        return this.timestamp;
    }

    public ChatMessageStatus getStatus() {
        return this.status;
    }

    public void setContent(String newContent) {
        this.content = newContent;
    }

    public void setStatus(ChatMessageStatus messageStatus) {
        this.status = messageStatus;
    }

    public static List<ChatMessage> findLastMessages(Long roomId, int limit) {
        return ChatMessage.find(
                        "room.id = ?1 ORDER BY id DESC", roomId
                )
                .page(Page.ofSize(limit))
                .list();
    }

    public static List<ChatMessage> findMessagesBefore(Long roomId, Long beforeMessageId, int limit) {
        return ChatMessage.find(
                        "room.id = ?1 AND id < ?2 ORDER BY id DESC", roomId, beforeMessageId
                )
                .page(Page.ofSize(limit))
                .list();
    }
}
