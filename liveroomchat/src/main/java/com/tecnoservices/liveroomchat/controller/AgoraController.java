package com.tecnoservices.liveroomchat.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.service.AgoraService;
import com.tecnoservices.liveroomchat.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/streaming")
@RequiredArgsConstructor
public class AgoraController {

    private final AgoraService agoraService;
    private final UserService userService; // Para validar al usuario actual

    @GetMapping("/token")
    public ResponseEntity<Map<String, String>> getToken(
            @RequestParam String channelName,
            @AuthenticationPrincipal User currentUser // Spring Security inyecta al usuario logueado
    ) {
        // 1. Validaciones de negocio (ej. ¿El usuario tiene saldo? ¿No está baneado?)
        if (currentUser.isBanned()) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        if (!userService.canStream(currentUser)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // 2. Generar token
        String token = agoraService.generateToken(channelName, currentUser.getId().toString());

        // 3. Respuesta JSON
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        response.put("appId", "tu_app_id_publico"); // A veces el front lo necesita

        return ResponseEntity.ok(response);
    }

}
