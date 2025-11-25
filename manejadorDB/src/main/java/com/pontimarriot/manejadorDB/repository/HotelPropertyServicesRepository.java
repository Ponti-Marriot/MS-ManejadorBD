package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.HotelPropertyServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface HotelPropertyServicesRepository extends JpaRepository<HotelPropertyServices, UUID> {

    List<HotelPropertyServices> findByServiceId(UUID serviceId);

    List<HotelPropertyServices> findByHotelPropertyId(UUID hotelPropertyId);
}
