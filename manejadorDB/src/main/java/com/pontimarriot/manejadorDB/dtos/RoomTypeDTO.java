package com.pontimarriot.manejadorDB.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomTypeDTO {
    private String id;
    private String name;
    private String description;
}
