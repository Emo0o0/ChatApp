package com.example.api.inputoutput.login;

import com.example.api.base.OperationResponse;

public record LoginChatUserResponse(Long id, String username) implements OperationResponse {
}
