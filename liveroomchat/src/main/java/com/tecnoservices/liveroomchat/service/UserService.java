package com.tecnoservices.liveroomchat.service;

import org.springframework.stereotype.Service;

import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public boolean canStream(User user) {
        return user.isStreamerEligible();
    }

    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
    }
}
