package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "hotelpropertiesrooms")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelPropertyRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "hotelProperty_id")
    private UUID hotelPropertyId;

    @Column(name = "room_id")
    private UUID roomId;

    private Integer bedrooms;

    private Integer bathrooms;

    @Column(name = "price_per_night")
    private BigDecimal pricePerNight;

    @Column(name = "hotelpropertiesrooms_id")
    private UUID hotelpropertiesroomsId;
}
