package com.example.core.service;

import com.example.api.inputoutput.get_old_msgs.GetOldMessagesListResponse;
import com.example.api.inputoutput.get_old_msgs.GetOldMessagesOperation;
import com.example.api.inputoutput.get_old_msgs.GetOldMessagesRequest;
import com.example.api.inputoutput.get_old_msgs.GetOldMessagesResponse;
import com.example.persistence.entity.ChatMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class GetOldMessagesProcessor implements GetOldMessagesOperation {
    @Override
    @Transactional
    public GetOldMessagesListResponse process(GetOldMessagesRequest request) {

        List<ChatMessage> messages = ChatMessage.findMessagesBefore(request.roomId(), request.messageId(), request.limit());
        return new GetOldMessagesListResponse(messages.stream()
                .map(msg -> new GetOldMessagesResponse(msg.id, msg.getSender().id, msg.getContent(), msg.getTimestamp()))
                .toList()
        );
    }
}
