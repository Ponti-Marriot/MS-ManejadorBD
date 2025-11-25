package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomJpaRepository extends JpaRepository<Room, UUID> {
    List<Room> findByAvailabilityDatesId(UUID availabilityDatesId);
}
