package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record StatusDTO(
        UUID id,
        String name,
        String created_at
) {
}
