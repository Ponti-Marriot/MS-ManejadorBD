package com.pontimarriot.manejadorDB.model;

import lombok.Data;

import java.util.UUID;

@Data
public class ReservaRequest {
    UUID hotel_id;
    String room_type;
}
