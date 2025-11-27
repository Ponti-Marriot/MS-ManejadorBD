package com.pontimarriot.manejadorDB.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class RegionDTO {
    private UUID locationId;
    private String countryCode;
    private String state;
    private String cityCode;
    private String cityName;
    private String timezone;
    private String subdivCode;
}
