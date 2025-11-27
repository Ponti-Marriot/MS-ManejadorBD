package com.pontimarriot.manejadorDB.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class HotelSummaryDTO {

    private UUID hotelId;
    private String name;
    private String address;
    private Integer stars;

    private UUID locationId;
    private String city;
    private String cityCode;
    private String country;
    private String region;
}
