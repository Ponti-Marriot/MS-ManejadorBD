package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record RoomCreationRequestDTO(
        UUID hotel_property_id,
        String room_type,
        int bedrooms,
        int bathrooms,
        double price_per_night
) {
}
