package com.tecnoservices.liveroomchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.model.Room;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.service.RoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> createRoom(
            @org.springframework.web.bind.annotation.RequestBody com.tecnoservices.liveroomchat.dto.RoomRequest request,
            @AuthenticationPrincipal User currentUser) {

        Room room = roomService.createRoom(currentUser.getId(), request.getTitle(), request.getType());
        return ResponseEntity.ok(room);
    }
}
