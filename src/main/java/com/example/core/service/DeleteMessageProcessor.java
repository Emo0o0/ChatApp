package com.example.core.service;

import com.example.api.inputoutput.message.delete.DeleteMessageOperation;
import com.example.api.inputoutput.message.delete.DeleteMessageRequest;
import com.example.api.inputoutput.message.delete.DeleteMessageResponse;
import com.example.core.exception.ChatMessageNotFoundException;
import com.example.core.exception.UnauthorisedResourceAccessException;
import com.example.persistence.entity.ChatMessage;
import com.example.persistence.entity.ChatMessageStatus;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class DeleteMessageProcessor implements DeleteMessageOperation {
    @Override
    @Transactional
    public DeleteMessageResponse process(DeleteMessageRequest request) {
        validate(request);
        ChatMessage cm = ChatMessage.findById(request.messageId());
        cm.setStatus(ChatMessageStatus.DELETED);
        ChatMessage.persist(cm);

        return new DeleteMessageResponse(true);
    }

    private void validate(DeleteMessageRequest request) {
        ChatMessage cm = ChatMessage.findById(request.messageId());
        if (cm == null)
            throw new ChatMessageNotFoundException("Message with id [" + request.messageId() + "] was not found");
        if (!cm.getSender().id.equals(ChatUserContext.getAuthenticatedUser().id))
            throw new UnauthorisedResourceAccessException("User with id [" + ChatUserContext.getAuthenticatedUser().id + "] tried to delete someone else's message");

    }
}
