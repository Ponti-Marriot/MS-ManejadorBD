package com.pontimarriot.manejadorDB.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.HotelProperty;

@Repository
public interface HotelPropertyRepository extends JpaRepository<HotelProperty, UUID> {
        // Para /location/{locationId}
    List<HotelProperty> findByLocationId(UUID locationId);

    // Para /availability/{availabilityDatesId}
    List<HotelProperty> findByAvailabilityDatesId(UUID availabilityDatesId);
}