package com.tecnoservices.liveroomchat.controller;

import com.tecnoservices.liveroomchat.config.JwtUtil;
import com.tecnoservices.liveroomchat.dto.AuthRequest;
import com.tecnoservices.liveroomchat.dto.AuthResponse;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody AuthRequest request) {
        var user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setStreamerEligible(true); // Auto-enable strictly for testing purposes
        userRepository.save(user);

        var jwtToken = jwtUtil.generateToken(userDetailsService.loadUserByUsername(user.getEmail()));
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        var userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        var jwtToken = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}
