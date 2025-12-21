package com.example.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity(name = "chat_users")
public class ChatUser extends PanacheEntity {

    @Column(unique = true, nullable = false)
    public String username;
}
