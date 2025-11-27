package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.HotelPropertyRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface HotelPropertyRoomRepository extends JpaRepository<HotelPropertyRoom, UUID> {

    // Buscar la relación entre hotel y habitación
    List<HotelPropertyRoom> findByHotelPropertyIdAndRoomId(UUID hotelPropertyId, UUID roomId);

    List<HotelPropertyRoom> findByHotelPropertyId(UUID hotelPropertyId);

    List<HotelPropertyRoom> findByRoomId(UUID roomId);
}
