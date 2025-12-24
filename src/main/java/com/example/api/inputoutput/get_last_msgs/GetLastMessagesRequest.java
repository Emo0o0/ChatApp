package com.example.api.inputoutput.get_last_msgs;

import com.example.api.base.OperationRequest;

public record GetLastMessagesRequest(Long roomId, int limit) implements OperationRequest {


}
