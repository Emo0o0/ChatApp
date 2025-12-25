package com.example.api.inputoutput.message.get_last;

import com.example.api.base.OperationRequest;

public record GetLastMessagesRequest(Long roomId, int limit) implements OperationRequest {


}
