package com.tecnoservices.liveroomchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnoservices.liveroomchat.model.Post;
import com.tecnoservices.liveroomchat.model.User;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserOrderByTimestampDesc(User user);
}
