package com.tecnoservices.liveroomchat.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Table(name = "pk_battles")
@Data
public class PkBattle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User challenger;

    @ManyToOne
    private User opponent;

    private String status; // REQUESTED, ACTIVE, ENDED

    private LocalDateTime startTime;
    private LocalDateTime endTime;

    @ManyToOne
    private User winner;
}
