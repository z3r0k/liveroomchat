package com.tecnoservices.liveroomchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tecnoservices.liveroomchat.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    java.util.Optional<User> findByUsername(String username);

    java.util.Optional<User> findByEmail(String email);
}
