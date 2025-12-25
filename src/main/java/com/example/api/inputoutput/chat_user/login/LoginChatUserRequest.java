package com.example.api.inputoutput.chat_user.login;

import com.example.api.base.OperationRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginChatUserRequest(@Email String email, @NotBlank String password) implements OperationRequest {
}
