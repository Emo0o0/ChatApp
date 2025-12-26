package com.example.rest.controller;

import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberOperation;
import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberRequest;
import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatOperation;
import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatRequest;
import com.example.core.service.ChatRoomService;
import com.example.persistence.entity.ChatUser;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;

@Path("/chat")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ChatRoomResource {

    @Inject
    ChatRoomService chatRoomService;
    @Inject
    CreateGroupChatOperation createGroupChatOperation;
    @Inject
    GroupChatAddMemberOperation groupChatAddMemberOperation;

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

    @POST
    @Path("/group")
    public Response createGroupChat(@RequestBody CreateGroupChatRequest request) {
        return Response.status(201)
                .entity(createGroupChatOperation.process(request))
                .build();
    }

    @POST
    @Path("/group/addMember")
    @Transactional
    public Response addMemberToGroupChat(@RequestBody GroupChatAddMemberRequest request) {
        return Response.status(201)
                .entity(groupChatAddMemberOperation.process(request))
                .build();
    }
}
