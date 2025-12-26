package com.example.core.service;

import com.example.api.inputoutput.message.edit.EditMessageOperation;
import com.example.api.inputoutput.message.edit.EditMessageRequest;
import com.example.api.inputoutput.message.edit.EditMessageResponse;
import com.example.core.exception.ChatMessageNotFoundException;
import com.example.core.exception.UnauthorisedResourceAccessException;
import com.example.core.service.ChatUserContext;
import com.example.persistence.entity.ChatMessage;
import com.example.persistence.entity.ChatMessageStatus;
import com.example.persistence.entity.ChatUser;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class EditMessageProcessor implements EditMessageOperation {
    @Override
    @Transactional
    public EditMessageResponse process(EditMessageRequest request) {
        validate(request);
        ChatMessage cm = ChatMessage.findById(request.messageId());
        cm.setContent(request.newContent());
        cm.setStatus(ChatMessageStatus.EDITED);
        ChatMessage.persist(cm);

        return new EditMessageResponse(true);
    }

    private void validate(EditMessageRequest request) {
        ChatMessage cm = ChatMessage.findById(request.messageId());
        if (cm == null)
            throw new ChatMessageNotFoundException("Message with id [" + request.messageId() + "] was not found");
        if (!cm.getSender().id.equals(ChatUserContext.getAuthenticatedUser().id))
            throw new UnauthorisedResourceAccessException("User with id [" + ChatUserContext.getAuthenticatedUser().id + "] tried to edit someone else's message");

    }
}
