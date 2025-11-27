package com.pontimarriot.manejadorDB.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pontimarriot.manejadorDB.model.HotelProperty;

@Repository
public interface HotelPropertyRepository extends JpaRepository<HotelProperty, UUID> {
    List<HotelProperty> findByLocationId(UUID locationId);

    List<HotelProperty> findByName(String name);

    Optional<HotelProperty> findByAddress(String address);
}