package com.example.persistence.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

@Entity(name = "chat_users")
public class ChatUser extends PanacheEntity {

    @Column(nullable = false)
    public String username;
    @Column(unique = true, nullable = false)
    public String email;
    @Column(unique = true, nullable = false)
    public String password;

    public ChatUser() {
    }

    public ChatUser(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}
