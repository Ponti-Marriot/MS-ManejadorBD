package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "hotelpropertiesrooms")
public class HotelPropertyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "hotel_property_id")
    private UUID hotelPropertyId;
    @Column(name = "room_id")
    private UUID roomId;
    @Column(name = "bedrooms")
    private int bedrooms;
    @Column(name = "bathrooms")
    private int bathrooms;
    @Column(name = "price_per_night")
    private double pricePerNight;

    public HotelPropertyRoom() {
    }

    public HotelPropertyRoom(UUID hotelPropertyId, UUID roomId, int bedrooms, int bathrooms, double pricePerNight) {
        this.hotelPropertyId = hotelPropertyId;
        this.roomId = roomId;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.pricePerNight = pricePerNight;
    }
}