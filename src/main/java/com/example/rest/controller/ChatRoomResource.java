package com.example.rest.controller;

import com.example.core.service.GetLastMessagesProcessor;
import com.example.api.inputoutput.message.get_last.GetLastMessagesRequest;
import com.example.core.service.GetOldMessagesProcessor;
import com.example.api.inputoutput.message.get_older.GetOldMessagesRequest;
import com.example.core.service.ChatRoomService;
import com.example.persistence.entity.ChatUser;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatRoomResource {

    @Inject
    ChatRoomService chatRoomService;

    @Inject
    GetLastMessagesProcessor getLastMessagesProcessor;
    @Inject
    GetOldMessagesProcessor getOldMessagesProcessor;

    @POST
    @Path("/private/{username1}/{username2}")
    @Transactional
    public Response openPrivateChat(@PathParam("username1") String username1,
                                    @PathParam("username2") String username2) {

        ChatUser me = ChatUser.find("username", username1).firstResult();
        ChatUser other = ChatUser.find("username", username2).firstResult();

        return Response.status(200)
                .entity(chatRoomService.getOrCreatePrivateRoom(me, other))
                .build();
    }

    @GET
    @Path("/last/{roomId}_{limit}")
    public Response getLastMessages(@PathParam("roomId") Long roomId,
                                    @PathParam("limit") int limit) {
        GetLastMessagesRequest request = new GetLastMessagesRequest(roomId, limit);
        return Response.status(200)
                .entity(getLastMessagesProcessor.process(request))
                .build();
    }

    @GET
    @Path("/last/{roomId}_{msgId}_{limit}")
    public Response getBeforeMessages(@PathParam("roomId") Long roomId,
                                      @PathParam("msgId") Long msgId,
                                      @PathParam("limit") int limit) {
        GetOldMessagesRequest request = new GetOldMessagesRequest(roomId, msgId, limit);
        return Response.status(200)
                .entity(getOldMessagesProcessor.process(request))
                .build();
    }
}
