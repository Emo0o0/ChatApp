package com.example.core.exception;

public class ChatRoomMemberNotFoundException extends RuntimeException{
    public ChatRoomMemberNotFoundException(String message) {
        super(message);
    }
}
