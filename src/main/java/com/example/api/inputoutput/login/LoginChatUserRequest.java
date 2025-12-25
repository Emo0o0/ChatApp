package com.example.api.inputoutput.login;

import com.example.api.base.OperationRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginChatUserRequest(@Email String email, @NotBlank String password) implements OperationRequest {
}
