package com.example.api.inputoutput.message.get_last;

import java.time.Instant;

public record GetLastMessagesResponse(Long id ,Long senderId, String content, Instant timestamp) {
}
