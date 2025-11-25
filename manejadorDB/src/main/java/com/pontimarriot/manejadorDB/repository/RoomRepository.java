package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, UUID> {

    // Buscar habitaciones de un tipo específico
    List<Room> findByRoomType(String room_type);
}

