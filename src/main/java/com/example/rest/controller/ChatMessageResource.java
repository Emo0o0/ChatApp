package com.example.rest.controller;

import com.example.api.inputoutput.message.delete.DeleteMessageOperation;
import com.example.api.inputoutput.message.delete.DeleteMessageRequest;
import com.example.api.inputoutput.message.edit.EditMessageOperation;
import com.example.api.inputoutput.message.edit.EditMessageRequest;
import jakarta.inject.Inject;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/chat")
public class ChatMessageResource {

    @Inject
    EditMessageOperation editMessageOperation;
    @Inject
    DeleteMessageOperation deleteMessageOperation;

    @PATCH
    @Path("/edit")
    public Response editMessage(@RequestBody EditMessageRequest request) {
        return Response.status(200)
                .entity(editMessageOperation.process(request))
                .build();
    }

    @DELETE
    @Path("/delete")
    public Response deleteMessage(@RequestBody DeleteMessageRequest request) {
        return Response.status(200)
                .entity(deleteMessageOperation.process(request))
                .build();
    }

}
