package com.pontimarriot.manejadorDB.dtos;

import java.util.UUID;

public record ServiceDTO(
        UUID id,
        String name,
        String category,
        String text
) {
}
