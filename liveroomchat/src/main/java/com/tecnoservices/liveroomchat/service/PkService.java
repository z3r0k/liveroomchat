package com.tecnoservices.liveroomchat.service;

import org.springframework.stereotype.Service;
import com.tecnoservices.liveroomchat.model.PkBattle;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.repository.PkBattleRepository;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PkService {

    private final PkBattleRepository pkBattleRepository;
    private final UserService userService;

    public PkBattle requestBattle(Long challengerId, Long opponentId) {
        User challenger = userService.findById(challengerId);
        User opponent = userService.findById(opponentId);

        PkBattle battle = new PkBattle();
        battle.setChallenger(challenger);
        battle.setOpponent(opponent);
        battle.setStatus("REQUESTED");
        return pkBattleRepository.save(battle);
    }

    public PkBattle acceptBattle(Long battleId) {
        PkBattle battle = pkBattleRepository.findById(battleId)
                .orElseThrow(() -> new RuntimeException("Battle not found"));
        battle.setStatus("ACTIVE");
        battle.setStartTime(LocalDateTime.now());
        return pkBattleRepository.save(battle);
    }

    public PkBattle endBattle(Long battleId, Long winnerId) {
        PkBattle battle = pkBattleRepository.findById(battleId)
                .orElseThrow(() -> new RuntimeException("Battle not found"));
        User winner = userService.findById(winnerId);

        battle.setStatus("ENDED");
        battle.setEndTime(LocalDateTime.now());
        battle.setWinner(winner);
        return pkBattleRepository.save(battle);
    }
}
