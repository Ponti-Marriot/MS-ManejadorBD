package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class HotelProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String description;
    private int squareMeters;
    private int stars;
    private String propertyType;
    private UUID locationId;
    private String address;
    private UUID availabilityDatesId;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<String> photosUrls;

    public HotelProperty(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        photosUrls = new ArrayList<>();
    }

    public HotelProperty(String name, String description, int squareMeters, int stars, String propertyType, UUID locationId, String address, UUID availabilityDatesId, List<String> photosUrls) {
        this.name = name;
        this.description = description;
        this.squareMeters = squareMeters;
        this.stars = stars;
        this.propertyType = propertyType;
        this.locationId = locationId;
        this.address = address;
        this.availabilityDatesId = availabilityDatesId;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.photosUrls = photosUrls;
    }
}
