package com.example.core.exception;

public class ChatUserAlreadyExistsException extends RuntimeException{
    public ChatUserAlreadyExistsException(String message) {
        super(message);
    }
}
