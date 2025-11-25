package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record HotelPropertyServicesDTO(
        UUID id,
        UUID service_id,
        UUID hotel_property_id
) {
}
