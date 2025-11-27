package com.pontimarriot.manejadorDB.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
public class ReservationDetailsDTO {

    private UUID id;
    private String reservationNumber;

    // Guest
    private String guestName;
    private String guestId;
    private String observations;

    // Dates
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private String currency;

    // Amount
    private BigDecimal totalAmount;
    private String status;
    private LocalDateTime cancelledAt;

    // Room info
    private UUID roomId;
    private String roomTitle;
    private String roomType;
    private Integer bedrooms;
    private Integer bathrooms;
    private BigDecimal pricePerNight;

    // Hotel info
    private UUID hotelId;
    private String hotelName;
    private String hotelAddress;
    private String city;
    private String country;
    private String region;
}
