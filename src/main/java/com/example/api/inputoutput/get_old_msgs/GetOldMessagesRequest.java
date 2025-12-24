package com.example.api.inputoutput.get_old_msgs;

import com.example.api.base.OperationRequest;

public record GetOldMessagesRequest(Long roomId, Long messageId, int limit) implements OperationRequest {

}
