package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "reservations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private UUID id;

    @Column(name = "guestid")
    private String guestID;

    @Column(name = "hotel_id")
    private UUID hotelId;

    @Column(name = "room_id")
    private UUID roomId;

    @Column(name = "check_in")
    private Date checkIn;

    @Column(name = "check_out")
    private Date checkOut;

    @Column(name = "total_price")
    private BigDecimal price;

    @Column(name = "status")
    private String status;

    @Column(name = "currency")
    private String currency;
}