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
public class HotelPropertyServices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID serviceId;
    private UUID hotelPropertyId;
    private String createdAt;

    public HotelPropertyServices() {
    }
    public HotelPropertyServices(UUID serviceId, UUID hotelPropertyId, String createdAt) {
        this.serviceId = serviceId;
        this.hotelPropertyId = hotelPropertyId;
        this.createdAt = createdAt;
    }
}
