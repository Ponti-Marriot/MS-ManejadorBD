package com.pontimarriot.manejadorDB.model.Propiedades.Entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String title;
    private String description;
    private String roomType;
    private UUID availabilityDatesId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Room(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    public Room(String title, String description, String roomType, UUID availabilityDatesId) {
        this.title = title;
        this.description = description;
        this.roomType = roomType;
        this.availabilityDatesId = availabilityDatesId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
}
