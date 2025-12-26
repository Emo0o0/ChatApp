package com.example.core.service;

import com.example.api.inputoutput.chat_user.register.RegisterChatUserOperation;
import com.example.api.inputoutput.chat_user.register.RegisterChatUserRequest;
import com.example.api.inputoutput.chat_user.register.RegisterChatUserResponse;
import com.example.core.exception.ChatUserAlreadyExistsException;
import com.example.persistence.entity.ChatUser;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RegisterChatUserProcessor implements RegisterChatUserOperation {
    @Override
    @Transactional
    public RegisterChatUserResponse process(RegisterChatUserRequest request) {
        validate(request);
        ChatUser cu = new ChatUser(request.username(), request.email(), BcryptUtil.bcryptHash(request.password()));
        ChatUser.persist(cu);
        return new RegisterChatUserResponse(cu.id);
    }

    private void validate(RegisterChatUserRequest request) {
        if (ChatUser.count("email", request.email()) > 0)
            throw new ChatUserAlreadyExistsException("User with email [" + request.email() + "] already exists");
    }
}
