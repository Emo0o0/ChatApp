package com.example.api.inputoutput.get_old_msgs;

import com.example.api.base.OperationResponse;

import java.util.List;

public record GetOldMessagesListResponse(List<GetOldMessagesResponse> messages) implements OperationResponse {
}
