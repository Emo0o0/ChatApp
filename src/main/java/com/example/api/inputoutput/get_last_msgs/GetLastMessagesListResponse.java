package com.example.api.inputoutput.get_last_msgs;

import com.example.api.base.OperationResponse;

import java.util.List;

public record GetLastMessagesListResponse(List<GetLastMessagesResponse> messages) implements OperationResponse {
}
