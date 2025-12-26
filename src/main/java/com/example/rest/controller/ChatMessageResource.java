package com.example.rest.controller;

import com.example.api.inputoutput.message.delete.DeleteMessageOperation;
import com.example.api.inputoutput.message.delete.DeleteMessageRequest;
import com.example.api.inputoutput.message.edit.EditMessageOperation;
import com.example.api.inputoutput.message.edit.EditMessageRequest;
import com.example.api.inputoutput.message.get_last.GetLastMessagesOperation;
import com.example.api.inputoutput.message.get_last.GetLastMessagesRequest;
import com.example.api.inputoutput.message.get_older.GetOldMessagesOperation;
import com.example.api.inputoutput.message.get_older.GetOldMessagesRequest;
import com.example.core.exception.ChatMessageNotFoundException;
import com.example.core.exception.UnauthorisedResourceAccessException;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/chat")
public class ChatMessageResource {

    @Inject
    EditMessageOperation editMessageOperation;
    @Inject
    DeleteMessageOperation deleteMessageOperation;
    @Inject
    GetLastMessagesOperation getLastMessagesOperation;
    @Inject
    GetOldMessagesOperation getOldMessagesOperation;

    @PATCH
    @Path("/edit")
    public Response editMessage(@RequestBody EditMessageRequest request) {
        try {
            return Response.status(200)
                    .entity(editMessageOperation.process(request))
                    .build();
        } catch (ChatMessageNotFoundException | UnauthorisedResourceAccessException e) {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("/delete")
    public Response deleteMessage(@RequestBody DeleteMessageRequest request) {
        try {
            return Response.status(200)
                    .entity(deleteMessageOperation.process(request))
                    .build();
        } catch (ChatMessageNotFoundException | UnauthorisedResourceAccessException e) {
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/last/{roomId}_{limit}")
    public Response getLastMessages(@PathParam("roomId") Long roomId,
                                    @PathParam("limit") int limit) {
        GetLastMessagesRequest request = new GetLastMessagesRequest(roomId, limit);
        return Response.status(200)
                .entity(getLastMessagesOperation.process(request))
                .build();
    }

    @GET
    @Path("/last/{roomId}_{msgId}_{limit}")
    public Response getBeforeMessages(@PathParam("roomId") Long roomId,
                                      @PathParam("msgId") Long msgId,
                                      @PathParam("limit") int limit) {
        GetOldMessagesRequest request = new GetOldMessagesRequest(roomId, msgId, limit);
        return Response.status(200)
                .entity(getOldMessagesOperation.process(request))
                .build();
    }

}
