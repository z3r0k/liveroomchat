package com.tecnoservices.liveroomchat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tecnoservices.liveroomchat.model.Follow;
import com.tecnoservices.liveroomchat.model.User;
import java.util.List;
import java.util.Optional;

public interface FollowRepository extends JpaRepository<Follow, Long> {
    Optional<Follow> findByFollowerAndFollowing(User follower, User following);

    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowing(User following);

    long countByFollowing(User following);
}
