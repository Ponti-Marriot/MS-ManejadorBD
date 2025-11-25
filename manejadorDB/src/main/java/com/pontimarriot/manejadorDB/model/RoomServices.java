package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "roomservices")
public class RoomServices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "service_id")
    private UUID serviceId;
    @Column(name = "room_id")
    private UUID roomId;
    @Column(name = "created_at")
    private String createdAt;

    public RoomServices() {
    }
    public RoomServices(UUID serviceId, UUID roomId, String createdAt) {
        this.serviceId = serviceId;
        this.roomId = roomId;
        this.createdAt = createdAt;
    }
}
