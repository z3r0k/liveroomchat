package com.tecnoservices.liveroomchat.dto;

import lombok.Data;

@Data
public class AgoraWebhookEvent {
    private String noticeId;
    private Long productId;
    private Long eventType; // 103 = Channel Destroy, 104 = Broadcaster Quit
    private Payload payload;

    @Data
    public static class Payload {
        private String channelName;
        // Other fields like 'uid', 'reason' exist but we focus on channelName for room
        // closure
    }
}
