package com.example.api.inputoutput.message.get_older;

import com.example.api.base.OperationResponse;

import java.util.List;

public record GetOldMessagesListResponse(List<GetOldMessagesResponse> messages) implements OperationResponse {
}
