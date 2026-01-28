package com.tecnoservices.liveroomchat.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.tecnoservices.liveroomchat.model.ChatMessage;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    // Unificado: React envía a /app/chat
    // El payload (ChatMessage) debe contener el roomId
    @MessageMapping("/chat")
    public void processMessage(@Payload ChatMessage chatMessage) {

        // 1. Lógica de Negocio (Validaciones, Cobros, Bad words filter, etc.)
        if (chatMessage.getType() == ChatMessage.MessageType.GIFT) {
            System.out.println(
                    "💰 Regalo recibido de: " + chatMessage.getSender() + " - Contenido: " + chatMessage.getContent());
            // Aquí llamarías a giftService.processGift(...)
            // Si falla el cobro, puedes retornar o enviar un error específico al usuario.
        } else if (chatMessage.getType() == ChatMessage.MessageType.JOIN) {
            System.out.println("👋 Nuevo usuario: " + chatMessage.getSender());
        }

        // 2. Construir destino dinámico: /topic/room/{roomId}
        String destination = "/topic/room/" + chatMessage.getRoomId();

        // 3. Enviar a la sala pública
        messagingTemplate.convertAndSend(destination, chatMessage);
    }

    // Private Chat: /app/chat/private -> /user/{username}/queue/messages
    @MessageMapping("/chat/private")
    public void sendPrivateMessage(@Payload ChatMessage chatMessage) {
        messagingTemplate.convertAndSendToUser(
                chatMessage.getReceiver(),
                "/queue/messages",
                chatMessage);
    }
}
