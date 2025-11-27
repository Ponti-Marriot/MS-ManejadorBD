package com.pontimarriot.manejadorDB.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateRoomRequestDTO {

    private UUID hotelPropertyId;

    private String title;
    private String description;
    private String roomType;

    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal pricePerNight;

    private List<UUID> roomServiceIds;
}
