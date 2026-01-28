package com.tecnoservices.liveroomchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnoservices.liveroomchat.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
