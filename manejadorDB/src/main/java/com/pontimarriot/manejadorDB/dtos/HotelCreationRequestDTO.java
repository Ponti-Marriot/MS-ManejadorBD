package com.pontimarriot.manejadorDB.dtos;

public record HotelCreationRequestDTO(
        String name,
        int stars,
        String property_type,
        String city,
        String address,
        String url
) {
}
