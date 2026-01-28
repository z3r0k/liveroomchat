package com.tecnoservices.liveroomchat.service;

import org.springframework.stereotype.Service;

import com.tecnoservices.liveroomchat.model.Follow;
import com.tecnoservices.liveroomchat.model.Post;
import com.tecnoservices.liveroomchat.model.User;
import com.tecnoservices.liveroomchat.repository.FollowRepository;
import com.tecnoservices.liveroomchat.repository.PostRepository;

import lombok.RequiredArgsConstructor;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SocialService {

    private final FollowRepository followRepository;
    private final PostRepository postRepository;
    private final UserService userService;

    public void followUser(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new RuntimeException("Cannot follow yourself");
        }
        User follower = userService.findById(followerId);
        User following = userService.findById(followingId);

        if (followRepository.findByFollowerAndFollowing(follower, following).isPresent()) {
            throw new RuntimeException("Already following");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowing(following);
        followRepository.save(follow);
    }

    public void unfollowUser(Long followerId, Long followingId) {
        User follower = userService.findById(followerId);
        User following = userService.findById(followingId);

        Follow follow = followRepository.findByFollowerAndFollowing(follower, following)
                .orElseThrow(() -> new RuntimeException("Not following"));

        followRepository.delete(follow);
    }

    public Post createPost(Long userId, String content, String mediaUrl) {
        User user = userService.findById(userId);
        Post post = new Post();
        post.setUser(user);
        post.setContent(content);
        post.setMediaUrl(mediaUrl);
        return postRepository.save(post);
    }

    public List<Post> getUserPosts(Long userId) {
        User user = userService.findById(userId);
        return postRepository.findByUserOrderByTimestampDesc(user);
    }
}
