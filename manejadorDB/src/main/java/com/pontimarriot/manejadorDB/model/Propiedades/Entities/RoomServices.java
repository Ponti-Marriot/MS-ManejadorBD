package com.pontimarriot.manejadorDB.model.Propiedades.Entities;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
public class RoomServices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID serviceId;
    private UUID roomId;
    private String createdAt;

    public RoomServices() {
    }
    public RoomServices(UUID serviceId, UUID roomId, String createdAt) {
        this.serviceId = serviceId;
        this.roomId = roomId;
        this.createdAt = createdAt;
    }
}
