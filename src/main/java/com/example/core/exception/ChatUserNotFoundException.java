package com.example.core.exception;

public class ChatUserNotFoundException extends RuntimeException{
    public ChatUserNotFoundException(String message) {
        super(message);
    }
}
