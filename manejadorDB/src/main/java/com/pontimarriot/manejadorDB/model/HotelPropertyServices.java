package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "hotelservices")
public class HotelPropertyServices {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "service_id")
    private UUID serviceId;
    @Column(name = "hotel_property_id")
    private UUID hotelPropertyId;

    public HotelPropertyServices() {
    }
    public HotelPropertyServices(UUID serviceId, UUID hotelPropertyId) {
        this.serviceId = serviceId;
        this.hotelPropertyId = hotelPropertyId;
    }
}
