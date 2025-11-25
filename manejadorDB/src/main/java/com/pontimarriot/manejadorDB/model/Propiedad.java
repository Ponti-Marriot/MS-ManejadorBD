package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "workers")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Propiedad {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    UUID id;
    String name;
    String description;
    int squareMeters;
    int stars;
    String property_type;
    UUID location_id;
    String address;
    UUID availability_dates_id;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    List<String> photosUrls;

}
