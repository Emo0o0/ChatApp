package com.example.rest.controller;

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
}
