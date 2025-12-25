package com.example.core.exception;

public class ChatUserInvalidPasswordException extends RuntimeException {
    public ChatUserInvalidPasswordException(String message) {
        super(message);
    }
}
