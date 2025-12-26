package com.example.rest.controller;

import com.example.api.inputoutput.message.edit.EditMessageOperation;
import com.example.api.inputoutput.message.edit.EditMessageRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/chat")
public class ChatMessageResource {

    @Inject
    EditMessageOperation editMessageOperation;

    @PATCH
    @Path("/edit")
    public Response editMessage(@RequestBody EditMessageRequest request) {
        return Response.status(200)
                .entity(editMessageOperation.process(request))
                .build();
    }

}
