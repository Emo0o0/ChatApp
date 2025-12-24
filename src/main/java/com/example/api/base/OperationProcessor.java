package com.example.api.base;

public interface OperationProcessor<R extends OperationRequest, P extends OperationResponse> {
    P process(R request);
}
