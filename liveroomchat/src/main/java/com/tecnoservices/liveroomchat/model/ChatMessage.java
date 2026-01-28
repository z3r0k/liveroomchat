package com.tecnoservices.liveroomchat.model;

import lombok.Data;

@Data
public class ChatMessage {
    private MessageType type;
    private String content;
    private String sender;
    private String receiver; // For private messages
    private Long roomId;

    public enum MessageType {
        CHAT,
        JOIN,
        LEAVE,
        DANMAKU,
        GIFT
    }
}
