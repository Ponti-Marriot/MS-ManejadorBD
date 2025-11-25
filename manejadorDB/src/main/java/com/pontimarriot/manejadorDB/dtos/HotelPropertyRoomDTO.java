package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record HotelPropertyRoomDTO(
        UUID id,
        UUID hotel_property_id,
        UUID room_id,
        int bedrooms,
        int bathrooms,
        double price_per_night
) {
}
