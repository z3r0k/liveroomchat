package com.tecnoservices.liveroomchat.dto;

import com.tecnoservices.liveroomchat.model.Post;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PostDTO {
    private Long id;
    private UserDTO user;
    private String content;
    private String mediaUrl;
    private int likesCount;
    private LocalDateTime timestamp;

    public static PostDTO fromEntity(Post post) {
        PostDTO dto = new PostDTO();
        dto.setId(post.getId());
        dto.setUser(UserDTO.fromEntity(post.getUser()));
        dto.setContent(post.getContent());
        dto.setMediaUrl(post.getMediaUrl());
        dto.setLikesCount(post.getLikesCount());
        dto.setTimestamp(post.getTimestamp());
        return dto;
    }
}
