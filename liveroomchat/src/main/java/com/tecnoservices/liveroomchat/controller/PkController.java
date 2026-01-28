package com.tecnoservices.liveroomchat.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tecnoservices.liveroomchat.model.PkBattle;
import com.tecnoservices.liveroomchat.service.PkService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/pk")
@RequiredArgsConstructor
public class PkController {

    private final PkService pkService;

    @PostMapping("/request")
    public ResponseEntity<PkBattle> requestBattle(
            @RequestParam Long challengerId,
            @RequestParam Long opponentId) {
        return ResponseEntity.ok(pkService.requestBattle(challengerId, opponentId));
    }

    @PostMapping("/accept")
    public ResponseEntity<PkBattle> acceptBattle(@RequestParam Long battleId) {
        return ResponseEntity.ok(pkService.acceptBattle(battleId));
    }

    @PostMapping("/end")
    public ResponseEntity<PkBattle> endBattle(
            @RequestParam Long battleId,
            @RequestParam Long winnerId) {
        return ResponseEntity.ok(pkService.endBattle(battleId, winnerId));
    }
}
