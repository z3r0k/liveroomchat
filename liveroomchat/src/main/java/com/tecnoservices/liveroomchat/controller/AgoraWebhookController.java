package com.tecnoservices.liveroomchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.dto.AgoraWebhookEvent;
import com.tecnoservices.liveroomchat.service.RoomService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/webhooks/agora")
@RequiredArgsConstructor
@Slf4j
public class AgoraWebhookController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<String> handleAgoraEvent(@RequestBody AgoraWebhookEvent event) {
        log.info("Received Agora Event: {}", event);

        // Event Type 103: Channel Destroyed (Last user left)
        // Event Type 104: Broadcaster Quit (Streamer left) - Depends on Agora config,
        // usually 103 is safer for channel closure
        if (event.getEventType() == 103 || event.getEventType() == 104) {
            if (event.getPayload() != null && event.getPayload().getChannelName() != null) {
                roomService.closeRoom(event.getPayload().getChannelName());
            }
        }

        return ResponseEntity.ok("OK");
    }
}
