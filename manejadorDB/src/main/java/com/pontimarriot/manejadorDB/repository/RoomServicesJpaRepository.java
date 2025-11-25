package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Propiedades.Entities.RoomServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoomServicesJpaRepository extends JpaRepository<RoomServices, UUID> {

    List<RoomServices> findByServiceId(UUID serviceId);

    List<RoomServices> findByRoomId(UUID roomId);
}
