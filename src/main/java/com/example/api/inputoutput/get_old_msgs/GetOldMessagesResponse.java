package com.example.api.inputoutput.get_old_msgs;


import java.time.Instant;

public record GetOldMessagesResponse(Long id, Long senderId, String content, Instant timestamp) {

}
