package com.pontimarriot.manejadorDB.model;

import lombok.Data;

import java.util.UUID;
import java.util.Date;

@Data
public class ReservaRequest {
    UUID hotel_id;
    String room_type;
    Date check_in;
    Date check_out;
    String document;
    Integer room_number;
    Integer adults_number;
}
