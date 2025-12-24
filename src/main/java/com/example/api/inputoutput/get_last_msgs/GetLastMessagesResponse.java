package com.example.api.inputoutput.get_last_msgs;

import java.time.Instant;

public record GetLastMessagesResponse(Long id ,Long senderId, String content, Instant timestamp) {
}
