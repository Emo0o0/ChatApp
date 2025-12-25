package com.example.api.inputoutput.register;

import com.example.persistence.entity.ChatUser;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class RegisterChatUserProcessor implements RegisterChatUserOperation {
    @Override
    @Transactional
    public RegisterChatUserResponse process(RegisterChatUserRequest request) {
        ChatUser cu = new ChatUser(request.username(), request.email(), BcryptUtil.bcryptHash(request.password()));
        ChatUser.persist(cu);
        return new RegisterChatUserResponse(cu.id);
    }
}
