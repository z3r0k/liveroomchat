package com.tecnoservices.liveroomchat.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnoservices.liveroomchat.model.Wallet;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
    Optional<Wallet> findByUserId(Long userId);
}
