package com.example.api.inputoutput.message.edit;

import com.example.api.base.OperationRequest;
import jakarta.validation.constraints.NotBlank;

public record EditMessageRequest(@NotBlank Long messageId, @NotBlank String newContent) implements OperationRequest {
}
