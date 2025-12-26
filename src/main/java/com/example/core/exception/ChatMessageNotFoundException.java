package com.example.core.exception;

public class ChatMessageNotFoundException extends RuntimeException{
    public ChatMessageNotFoundException(String message) {
        super(message);
    }
}
