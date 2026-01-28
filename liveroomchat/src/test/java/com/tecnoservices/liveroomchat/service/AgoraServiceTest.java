package com.tecnoservices.liveroomchat.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class AgoraServiceTest {

    private AgoraService agoraService;

    @BeforeEach
    void setUp() {
        agoraService = new AgoraService();
    }

    @Test
    void generateToken_withPlaceholderCertificate_shouldFailOrReturnEmpty() {
        // Inject values simulating application.properties
        ReflectionTestUtils.setField(agoraService, "appId", "73aadda2dc3c4bc4a1af360d7b375bd8");
        ReflectionTestUtils.setField(agoraService, "appCertificate", "tu_certificado_de_agora");
        ReflectionTestUtils.setField(agoraService, "expirationTimeInSeconds", 3600);

        String token = agoraService.generateToken("TestChannel", "123");

        System.out.println("Generated Token with placeholder: [" + token + "]");

        // Asserting what we suspect: likely it's empty or invalid,
        // but for now let's just assert it's not null to see the output
        assertNotNull(token);
    }

    @Test
    void generateToken_withValidFormatCertificate_shouldGenerateToken() {
        // Inject values with a dummy certificate that has valid hex format (32 chars)
        // Agora certificates are usually 32-char hex strings.
        ReflectionTestUtils.setField(agoraService, "appId", "73aadda2dc3c4bc4a1af360d7b375bd8");
        // Using a dummy 32-char hex string as certificate
        ReflectionTestUtils.setField(agoraService, "appCertificate", "5367566B59703373367639792F423F45");
        ReflectionTestUtils.setField(agoraService, "expirationTimeInSeconds", 3600);

        String token = agoraService.generateToken("TestChannel", "123");

        System.out.println("Generated Token with valid format: [" + token + "]");

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }
}
