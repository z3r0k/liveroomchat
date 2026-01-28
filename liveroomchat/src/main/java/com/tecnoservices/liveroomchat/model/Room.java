package com.tecnoservices.liveroomchat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rooms")
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String status;
    private String rtmpUrl;
    private String hlsUrl;
    private int maxViewers;
    private int currentViewers;

    private RoomType type = RoomType.VIDEO; // Default to VIDEO
    private boolean isPkActive = false;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public boolean isStreamerEligible() {
        return "STREAMER".equals(this.user.getRole());
    }

}
