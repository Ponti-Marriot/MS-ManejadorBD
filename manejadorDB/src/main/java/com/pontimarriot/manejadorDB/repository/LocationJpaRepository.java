package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Location;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationJpaRepository extends JpaRepository<Location, UUID> {

    Optional<Location> findFirstByCityName(String cityName);

    Optional<Location> findFirstByCityNameIgnoreCase(String cityName);

}
