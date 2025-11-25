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
    @Column(nullable = false)
    UUID reservationID;
    @Column(name = "guestid")
    String guestID;
    UUID HotelID;
    UUID roomId;
    Date checkIn;
    Date checkOut;
    @Column(name = "total_price")
    BigDecimal price;
    String status;
    String currency;
}
