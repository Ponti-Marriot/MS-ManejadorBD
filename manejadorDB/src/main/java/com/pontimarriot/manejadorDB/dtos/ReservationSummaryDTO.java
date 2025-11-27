package com.pontimarriot.manejadorDB.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationSummaryDTO {

    private UUID id;
    private String reservationNumber;
    private String guestName;
    private LocalDateTime checkIn;
    private LocalDateTime checkOut;
    private BigDecimal totalAmount;
    private String status;
}
