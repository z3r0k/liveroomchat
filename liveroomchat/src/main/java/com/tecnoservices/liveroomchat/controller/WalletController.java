package com.tecnoservices.liveroomchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.service.WalletService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/wallet")
@RequiredArgsConstructor
public class WalletController {

    private final WalletService walletService;

    @GetMapping("/balance")
    public ResponseEntity<com.tecnoservices.liveroomchat.dto.WalletDTO> getBalance(
            @AuthenticationPrincipal User currentUser) {
        return ResponseEntity.ok(com.tecnoservices.liveroomchat.dto.WalletDTO
                .fromEntity(walletService.getOrCreateWallet(currentUser.getId())));
    }

    @PostMapping("/recharge")
    public ResponseEntity<com.tecnoservices.liveroomchat.dto.WalletDTO> recharge(
            @AuthenticationPrincipal User currentUser,
            @RequestParam long amount) {
        return ResponseEntity.ok(com.tecnoservices.liveroomchat.dto.WalletDTO
                .fromEntity(walletService.rechargeCoins(currentUser.getId(), amount)));
    }
}
