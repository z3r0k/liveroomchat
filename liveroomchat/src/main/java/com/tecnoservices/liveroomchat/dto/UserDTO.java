package com.tecnoservices.liveroomchat.dto;

import com.tecnoservices.liveroomchat.model.User;
import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String username;
    private String avatarUrl;
    private int level;
    private boolean isVip;
    private boolean streamerEligible;
    private String bio;

    public static UserDTO fromEntity(User user) {
        if (user == null)
            return null;
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setUsername(user.getUsername());
        dto.setAvatarUrl(user.getAvatarUrl());
        dto.setLevel(user.getLevel());
        dto.setVip(user.isVip());
        dto.setStreamerEligible(user.isStreamerEligible());
        dto.setBio(user.getBio());
        return dto;
    }
}
