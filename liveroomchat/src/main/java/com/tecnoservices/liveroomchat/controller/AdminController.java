package com.tecnoservices.liveroomchat.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.service.AdminService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/ban")
    public ResponseEntity<String> banUser(@RequestParam Long userId) {
        adminService.banUser(userId);
        return ResponseEntity.ok("User banned");
    }

    @PostMapping("/unban")
    public ResponseEntity<String> unbanUser(@RequestParam Long userId) {
        adminService.unbanUser(userId);
        return ResponseEntity.ok("User unbanned");
    }

    @GetMapping("/stats")
    public ResponseEntity<Map<String, Object>> getStats() {
        return ResponseEntity.ok(adminService.getPlatformStats());
    }
}
