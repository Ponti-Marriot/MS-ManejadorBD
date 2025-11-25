package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record RoomServicesDTO(
        UUID id,
        UUID service_id,
        UUID room_id,
        String created_at
) {
}
