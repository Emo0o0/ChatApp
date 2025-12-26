package com.example.core.exception;

import jakarta.ws.rs.WebApplicationException;

public class ChatRoomMemberException extends WebApplicationException {
    public ChatRoomMemberException(String message, int status) {
        super(message, status);
    }
}
