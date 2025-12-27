package com.example.core.exception;

public class ChatRoomNotFoundException extends RuntimeException{
    public ChatRoomNotFoundException(String message) {
        super(message);
    }
}
