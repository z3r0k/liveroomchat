package com.tecnoservices.liveroomchat.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.tecnoservices.liveroomchat.model.Gift;
import com.tecnoservices.liveroomchat.model.Wallet;
import com.tecnoservices.liveroomchat.repository.GiftRepository;
import com.tecnoservices.liveroomchat.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;
    private final WalletService walletService;
    private final WalletRepository walletRepository;

    @Transactional
    public void sendGift(Long senderId, Long receiverId, Long giftId) {
        Gift gift = giftRepository.findById(giftId)
                .orElseThrow(() -> new RuntimeException("Gift not found"));

        Wallet senderWallet = walletService.getOrCreateWallet(senderId);
        Wallet receiverWallet = walletService.getOrCreateWallet(receiverId);

        if (senderWallet.getCoins() < gift.getCost()) {
            throw new RuntimeException("Insufficient coins");
        }

        // Deduct from sender
        senderWallet.setCoins(senderWallet.getCoins() - gift.getCost());
        walletRepository.save(senderWallet);
        walletService.createTransaction(senderWallet, gift.getCost(), "SPEND", "GIFT_" + gift.getId());

        // Calculate commission (50% to Platform, 50% to Streamer)
        double commissionRate = 0.5;
        long diamondsToSend = (long) (gift.getCost() * (1 - commissionRate));

        // Add to receiver
        receiverWallet.setDiamonds(receiverWallet.getDiamonds() + diamondsToSend);
        walletRepository.save(receiverWallet);
        walletService.createTransaction(receiverWallet, diamondsToSend, "EARN", "GIFT_" + gift.getId());
    }
}
