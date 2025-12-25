package com.example.api.inputoutput.chat_user.login;

import com.example.core.exception.ChatUserInvalidPasswordException;
import com.example.core.exception.ChatUserNotFoundException;
import com.example.core.service.ChatUserContext;
import com.example.persistence.entity.ChatUser;
import io.quarkus.elytron.security.common.BcryptUtil;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class LoginChatUserProcessor implements LoginChatUserOperation {
    @Override
    public LoginChatUserResponse process(LoginChatUserRequest request) {
        ChatUser cu = ChatUser.find("email", request.email()).firstResult();
        validateInput(request, cu);
        ChatUserContext.setUserContext(cu);
        return new LoginChatUserResponse(cu.id, cu.username);
    }

    private void validateInput(LoginChatUserRequest request, ChatUser cu) {
        if (cu == null)
            throw new ChatUserNotFoundException("User with email [" + request.email() + "] was not found");

        if (!BcryptUtil.matches(request.password(), cu.password))
            throw new ChatUserInvalidPasswordException("Wrong password for user [" + request.email() + "]");
    }
}
