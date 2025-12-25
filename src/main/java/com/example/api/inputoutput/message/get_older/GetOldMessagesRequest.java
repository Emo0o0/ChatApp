package com.example.api.inputoutput.message.get_older;

import com.example.api.base.OperationRequest;

public record GetOldMessagesRequest(Long roomId, Long messageId, int limit) implements OperationRequest {

}
