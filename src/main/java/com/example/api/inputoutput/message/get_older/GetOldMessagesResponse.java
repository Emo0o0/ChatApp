package com.example.api.inputoutput.message.get_older;


import java.time.Instant;

public record GetOldMessagesResponse(Long id, Long senderId, String content, Instant timestamp) {

}
