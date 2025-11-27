package com.pontimarriot.manejadorDB.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RoomDetailsDTO {

    // Room info
    private UUID roomId;
    private String roomTitle;
    private String roomDescription;
    private String roomType;

    // Hotel-property-room info
    private UUID hotelPropertyRoomId;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal pricePerNight;

    // Hotel info
    private UUID hotelId;
    private String hotelName;
    private String hotelAddress;
    private Integer hotelStars;

    // Location info
    private UUID locationId;
    private String city;
    private String cityCode;
    private String country;
    private String region;

    // Services
    private List<ServiceDTO> roomServices;
    private List<ServiceDTO> hotelServices;
}
