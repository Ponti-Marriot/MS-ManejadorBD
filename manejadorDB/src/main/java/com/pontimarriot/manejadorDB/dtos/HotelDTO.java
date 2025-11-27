package com.pontimarriot.manejadorDB.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDTO {
    private String id;
    private String name;
    private String city;
    private String region;
    private String country;
}

