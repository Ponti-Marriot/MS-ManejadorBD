package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "hotelproperties")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private int stars;
    @Column(name = "property_type")
    private String propertyType;
    @Column(name = "location_id")
    private UUID locationId;
    @Column(name = "address")
    private String address;
    @Column(name = "images_id")
    private UUID imagesId;
}
