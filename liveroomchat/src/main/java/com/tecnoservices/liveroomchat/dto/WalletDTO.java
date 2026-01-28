package com.tecnoservices.liveroomchat.dto;

import com.tecnoservices.liveroomchat.model.Wallet;
import lombok.Data;

@Data
public class WalletDTO {
    private Long userId;
    private long coins;
    private long diamonds;

    public static WalletDTO fromEntity(Wallet wallet) {
        WalletDTO dto = new WalletDTO();
        dto.setUserId(wallet.getUser().getId());
        dto.setCoins(wallet.getCoins());
        dto.setDiamonds(wallet.getDiamonds());
        return dto;
    }
}
