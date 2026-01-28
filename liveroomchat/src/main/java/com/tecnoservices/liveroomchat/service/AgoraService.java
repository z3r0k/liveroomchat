package com.tecnoservices.liveroomchat.service;

import io.agora.media.RtcTokenBuilder2;
import io.agora.media.RtcTokenBuilder2.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AgoraService {

    @Value("${agora.app.id}")
    private String appId;

    @Value("${agora.app.certificate}")
    private String appCertificate;

    @Value("${agora.token.expiration}")
    private int expirationTimeInSeconds;

    /**
     * Genera un token para que un usuario se una a un canal específico.
     * @param channelName El nombre único de la sala (Room ID).
     * @param userId El ID del usuario en tu base de datos.
     * @return String Token codificado.
     */
    public String generateToken(String channelName, String userId) {
        // RtcTokenBuilder2 es la clase que viene del SDK de Agora
        RtcTokenBuilder2 tokenBuilder = new RtcTokenBuilder2();
        
        // Calculamos el tiempo de expiración (Timestamp actual + segundos configurados)
        int timestamp = (int) (System.currentTimeMillis() / 1000 + expirationTimeInSeconds);

        // Definimos el rol: PUBLISHER (Streamer) o SUBSCRIBER (Espectador)
        // Para simplificar, aquí damos permisos de publisher, pero podrías restringirlo
        // según la lógica de tu negocio.
        return tokenBuilder.buildTokenWithUid(
            appId, 
            appCertificate, 
            channelName, 
            Integer.parseInt(userId), // Agora usa int para UIDs en su versión estándar
            Role.ROLE_PUBLISHER, 
            timestamp, 
            timestamp
        );
    }
}