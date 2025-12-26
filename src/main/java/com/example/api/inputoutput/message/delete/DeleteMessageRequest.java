package com.example.api.inputoutput.message.delete;

import com.example.api.base.OperationRequest;

public record DeleteMessageRequest(Long messageId) implements OperationRequest {
}

