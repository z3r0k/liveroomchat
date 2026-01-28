package com.tecnoservices.liveroomchat.dto;

import com.tecnoservices.liveroomchat.model.Room;
import com.tecnoservices.liveroomchat.model.RoomType;
import lombok.Data;

@Data
public class RoomDTO {
    private Long id;
    private String title;
    private String status;
    private RoomType type;
    private boolean isPkActive;
    private int currentViewers;
    private UserDTO streamer;

    public static RoomDTO fromEntity(Room room) {
        RoomDTO dto = new RoomDTO();
        dto.setId(room.getId());
        dto.setTitle(room.getTitle());
        dto.setStatus(room.getStatus());
        dto.setType(room.getType());
        dto.setPkActive(room.isPkActive());
        dto.setCurrentViewers(room.getCurrentViewers());
        dto.setStreamer(UserDTO.fromEntity(room.getUser()));
        return dto;
    }
}
