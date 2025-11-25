package com.pontimarriot.manejadorDB.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pontimarriot.manejadorDB.model.AvailabilityDates;

@Repository
public interface AvailabilityDatesRepository extends JpaRepository<AvailabilityDates, UUID> {
        // Método que usas en tu controller
    List<AvailabilityDates> findByRoomId(UUID roomId);
}