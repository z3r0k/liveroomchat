package com.tecnoservices.liveroomchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnoservices.liveroomchat.model.Gift;

public interface GiftRepository extends JpaRepository<Gift, Long> {
}
