package com.pontimarriot.manejadorDB.dtos;

import java.time.LocalDate;

public record AvailabilityQueryDTO(
        String ciudad_destino,
        LocalDate fecha_checkin,
        LocalDate fecha_checkout,
        int num_adultos,
        int num_habitaciones
) {
}
