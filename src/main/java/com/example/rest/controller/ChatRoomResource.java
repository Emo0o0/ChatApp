package com.example.rest.controller;

import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberOperation;
import com.example.api.inputoutput.chat_room.group.add_member.GroupChatAddMemberRequest;
import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatOperation;
import com.example.api.inputoutput.chat_room.group.create.CreateGroupChatRequest;
import com.example.api.inputoutput.chat_room.group.remove_member.GroupChatRemoveMemberOperation;
import com.example.api.inputoutput.chat_room.group.remove_member.GroupChatRemoveMemberRequest;
import com.example.api.inputoutput.chat_room.private_chat.get_or_create.GetOrCreatePrivateRoomRequest;
import com.example.core.exception.*;
import com.example.core.service.ChatRoomService;
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
    @Inject
    GroupChatRemoveMemberOperation groupChatRemoveMemberOperation;

    @POST
    @Path("/private/{userId1}/{userId2}")
    @Transactional
    public Response openPrivateChat(@PathParam("userId1") Long userId1,
                                    @PathParam("userId2") Long userId2) {
// userId1 is logged in user. Currently in endpoint for testing.
        GetOrCreatePrivateRoomRequest request = new GetOrCreatePrivateRoomRequest(userId1, userId2);
        try {
            return Response.status(200)
                    .entity(chatRoomService.process(request))
                    .build();
        } catch (ChatUserNotFoundException e) {
            return Response.status(400).build();
        }
    }

    @POST
    @Path("/group")
    public Response createGroupChat(@RequestBody CreateGroupChatRequest request) {
        try {
            return Response.status(201)
                    .entity(createGroupChatOperation.process(request))
                    .build();
        } catch (ChatUserNotFoundException e) {
            return Response.status(400).build();
        }
    }

    @POST
    @Path("/group/addMember")
    public Response addMemberToGroupChat(@RequestBody GroupChatAddMemberRequest request) {
        try {
            return Response.status(201)
                    .entity(groupChatAddMemberOperation.process(request))
                    .build();
        } catch (ChatRoomMemberException | ChatRoomAddException | ChatUserNotFoundException e) {
            return Response.status(400).build();
        }
    }

    @DELETE
    @Path("/group/removeMember")
    public Response removeMemberFromGroupChat(@RequestBody GroupChatRemoveMemberRequest request) {
        try {
            return Response.status(200)
                    .entity(groupChatRemoveMemberOperation.process(request))
                    .build();
        } catch (ChatRoomNotFoundException | ChatRoomRemoveException | ChatUserNotFoundException |
                 ChatRoomMemberNotFoundException e) {
            return Response.status(400).build();
        }
    }
}
