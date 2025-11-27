package com.pontimarriot.manejadorDB.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoomResponseDTO {
    private String id;
    private String roomNumber;
    private String title;
    private String description;
    private String roomType;
    private Double price;
    private Integer bedrooms;
    private Integer bathrooms;
    private String status;
    private HotelDTO hotel;
}
