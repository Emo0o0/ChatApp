package com.example.core.exception;

public class UnauthorisedResourceAccessException extends RuntimeException{
    public UnauthorisedResourceAccessException(String message) {
        super(message);
    }
}
