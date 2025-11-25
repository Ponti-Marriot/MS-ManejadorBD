package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.HotelPropertyRoom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HotelPropertyRoomRepository extends JpaRepository<HotelPropertyRoom, UUID> {

    List<HotelPropertyRoom> findByHotelPropertyId(UUID hotelPropertyId);

    List<HotelPropertyRoom> findByRoomId(UUID roomId);
}
