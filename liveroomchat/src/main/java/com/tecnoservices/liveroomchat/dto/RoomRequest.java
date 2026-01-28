package com.tecnoservices.liveroomchat.dto;

import com.tecnoservices.liveroomchat.model.RoomType;
import lombok.Data;

@Data
public class RoomRequest {
    private String title;
    private RoomType type = RoomType.VIDEO; // Default to VIDEO if not provided
}
