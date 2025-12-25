package com.example.api.inputoutput.register;

import com.example.api.base.OperationRequest;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;

public record RegisterChatUserRequest(@Email String email, @NotBlank @Length(max = 30) String password, @NotBlank String username) implements OperationRequest {
}
