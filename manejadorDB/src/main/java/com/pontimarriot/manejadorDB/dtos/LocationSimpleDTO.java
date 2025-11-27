package com.pontimarriot.manejadorDB.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class LocationSimpleDTO {
    private UUID id;
    private String cityName;
    private String state;
    private String countryCode;
    private String cityCode;
    private String timezone;
}