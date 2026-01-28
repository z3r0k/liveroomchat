package com.tecnoservices.liveroomchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.service.GiftService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/gifts")
@RequiredArgsConstructor
public class GiftController {

    private final GiftService giftService;

    @PostMapping("/send")
    public ResponseEntity<String> sendGift(
            @AuthenticationPrincipal User currentUser,
            @RequestParam Long receiverId,
            @RequestParam Long giftId) {

        giftService.sendGift(currentUser.getId(), receiverId, giftId);
        return ResponseEntity.ok("Gift sent successfully");
    }
}
