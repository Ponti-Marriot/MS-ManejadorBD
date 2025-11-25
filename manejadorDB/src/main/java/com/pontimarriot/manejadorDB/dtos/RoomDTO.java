package com.pontimarriot.manejadorDB.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

public record RoomDTO(
        UUID id,
        String title,
        String description,
        String room_type,
        LocalDateTime created_at,
        LocalDateTime updated_at
) {
}
