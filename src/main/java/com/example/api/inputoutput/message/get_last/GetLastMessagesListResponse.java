package com.example.api.inputoutput.message.get_last;

import com.example.api.base.OperationResponse;

import java.util.List;

public record GetLastMessagesListResponse(List<GetLastMessagesResponse> messages) implements OperationResponse {
}
