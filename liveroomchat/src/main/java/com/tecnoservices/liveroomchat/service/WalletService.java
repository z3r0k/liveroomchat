package com.tecnoservices.liveroomchat.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.tecnoservices.liveroomchat.model.Transaction;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.model.Wallet;
import com.tecnoservices.liveroomchat.repository.TransactionRepository;
import com.tecnoservices.liveroomchat.repository.WalletRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final UserService userService;

    public Wallet getOrCreateWallet(Long userId) {
        return walletRepository.findByUserId(userId)
                .orElseGet(() -> {
                    User user = userService.findById(userId);
                    Wallet wallet = new Wallet();
                    wallet.setUser(user);
                    return walletRepository.save(wallet);
                });
    }

    public Wallet rechargeCoins(Long userId, long amount) {
        Wallet wallet = getOrCreateWallet(userId);
        wallet.setCoins(wallet.getCoins() + amount);
        walletRepository.save(wallet);

        createTransaction(wallet, amount, "DEPOSIT", "RECHARGE");
        return wallet;
    }

    // Used by GiftService
    public void createTransaction(Wallet wallet, long amount, String type, String referenceId) {
        Transaction tx = new Transaction();
        tx.setWallet(wallet);
        tx.setAmount(amount);
        tx.setType(type);
        tx.setReferenceId(referenceId);
        tx.setTimestamp(LocalDateTime.now());
        transactionRepository.save(tx);
    }
}
