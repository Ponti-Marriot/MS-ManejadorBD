package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "hotelproperties")
public class HotelProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "stars")
    private int stars;
    @Column(name = "property_type")
    private String propertyType;
    @Column(name = "location_id")
    private UUID locationId;
    @Column(name = "address")
    private String address;
    @Column(name = "images_id")
    private UUID imagesId;

    public HotelProperty(){
    }

    public HotelProperty(String name, int stars, String propertyType, UUID locationId, String address, UUID imagesId) {
        this.name = name;
        this.stars = stars;
        this.propertyType = propertyType;
        this.locationId = locationId;
        this.address = address;
        this.imagesId = imagesId;
    }
}
