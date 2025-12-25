package com.example.core.service;

import com.example.api.inputoutput.get_last_msgs.GetLastMessagesListResponse;
import com.example.api.inputoutput.get_last_msgs.GetLastMessagesOperation;
import com.example.api.inputoutput.get_last_msgs.GetLastMessagesRequest;
import com.example.api.inputoutput.get_last_msgs.GetLastMessagesResponse;
import com.example.persistence.entity.ChatMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class GetLastMessagesProcessor implements GetLastMessagesOperation {
    @Override
    @Transactional
    public GetLastMessagesListResponse process(GetLastMessagesRequest request) {

        List<ChatMessage> messages = ChatMessage.findLastMessages(request.roomId(), request.limit());
        return new GetLastMessagesListResponse(messages.stream()
                .map(msg -> new GetLastMessagesResponse(msg.id, msg.getSender().id, msg.getContent(), msg.getTimestamp()))
                .toList()
        );

    }
}
