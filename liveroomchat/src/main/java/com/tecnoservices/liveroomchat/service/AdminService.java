package com.tecnoservices.liveroomchat.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public void banUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(true);
        userRepository.save(user);
    }

    public void unbanUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setBanned(false);
        userRepository.save(user);
    }

    public Map<String, Object> getPlatformStats() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalUsers", userRepository.count());
        // In a real app, we would query other repositories for active streams, daily
        // transaction volume, etc.
        stats.put("status", "System Operational");
        return stats;
    }
}
