package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record HotelPropertyDTO(
        UUID id,
        String name,
        int stars,
        String property_type,
        UUID location_id,
        String address,
        UUID images_id
) {}