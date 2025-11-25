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
public class HotelPropertyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID hotelPropertyId;
    private UUID roomId;
    private UUID statusId;
    private double squareMeters;
    private int bedrooms;
    private int bathrooms;
    private double pricePerNight;

    public HotelPropertyRoom() {
    }

    public HotelPropertyRoom(UUID hotelPropertyId, UUID roomId, UUID statusId, double squareMeters, int bedrooms, int bathrooms, double pricePerNight) {
        this.hotelPropertyId = hotelPropertyId;
        this.roomId = roomId;
        this.statusId = statusId;
        this.squareMeters = squareMeters;
        this.bedrooms = bedrooms;
        this.bathrooms = bathrooms;
        this.pricePerNight = pricePerNight;
    }
}