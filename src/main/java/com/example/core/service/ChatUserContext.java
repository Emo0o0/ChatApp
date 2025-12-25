package com.example.core.service;

import com.example.persistence.entity.ChatUser;

public final class ChatUserContext {

    private static ChatUser chatUser;

    public static void setUserContext(ChatUser chatUser) {
        ChatUserContext.chatUser = chatUser;
    }

    public static ChatUser getAuthenticatedUser() {
        return ChatUserContext.chatUser;
    }

}
