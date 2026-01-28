package com.tecnoservices.liveroomchat.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.model.Post;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.service.SocialService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/social")
@RequiredArgsConstructor
public class SocialController {

    private final SocialService socialService;

    @PostMapping("/follow")
    public ResponseEntity<String> followUser(
            @AuthenticationPrincipal User currentUser,
            @RequestParam Long targetId) {
        socialService.followUser(currentUser.getId(), targetId);
        return ResponseEntity.ok("Followed successfully");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(
            @AuthenticationPrincipal User currentUser,
            @RequestParam Long targetId) {
        socialService.unfollowUser(currentUser.getId(), targetId);
        return ResponseEntity.ok("Unfollowed successfully");
    }

    @PostMapping("/posts")
    public ResponseEntity<com.tecnoservices.liveroomchat.dto.PostDTO> createPost(
            @AuthenticationPrincipal User currentUser,
            @RequestParam String content,
            @RequestParam(required = false) String mediaUrl) {
        return ResponseEntity.ok(com.tecnoservices.liveroomchat.dto.PostDTO
                .fromEntity(socialService.createPost(currentUser.getId(), content, mediaUrl)));
    }

    @GetMapping("/posts/user")
    public ResponseEntity<List<com.tecnoservices.liveroomchat.dto.PostDTO>> getUserPosts(@RequestParam Long userId) {
        List<Post> posts = socialService.getUserPosts(userId);
        return ResponseEntity.ok(posts.stream().map(com.tecnoservices.liveroomchat.dto.PostDTO::fromEntity).toList());
    }
}
