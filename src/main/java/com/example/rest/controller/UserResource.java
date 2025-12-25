package com.example.rest.controller;

import com.example.api.inputoutput.chat_user.login.LoginChatUserOperation;
import com.example.api.inputoutput.chat_user.login.LoginChatUserRequest;
import com.example.api.inputoutput.chat_user.register.RegisterChatUserOperation;
import com.example.api.inputoutput.chat_user.register.RegisterChatUserRequest;
import jakarta.inject.Inject;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/user")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserResource {

    @Inject
    RegisterChatUserOperation registerChatUserOperation;
    @Inject
    LoginChatUserOperation loginChatUserOperation;

    @POST
    @Path("/register")
    public Response registerUser(@RequestBody @Valid RegisterChatUserRequest request) {
        return Response.status(201)
                .entity(registerChatUserOperation.process(request))
                .build();
    }

    @POST
    @Path("/login")
    public Response loginUser(@RequestBody @Valid LoginChatUserRequest request) {
        return Response.status(200)
                .entity(loginChatUserOperation.process(request))
                .build();
    }
}
