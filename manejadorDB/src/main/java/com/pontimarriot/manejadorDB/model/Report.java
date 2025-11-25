package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "reports_dashboard")
@Immutable
@org.hibernate.annotations.Subselect("SELECT * FROM reports_dashboard")
@Getter
@Setter
public class Report {

    @Id
    @Column(name = "id", updatable = false, insertable = false)
    private UUID id;

    @Column(name = "hotel_id", updatable = false, insertable = false)
    private UUID hotelId;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDate createdAt;

    @Column(name = "total_reservations", updatable = false, insertable = false)
    private Long totalReservations;

    @Column(name = "total_revenue", updatable = false, insertable = false)
    private BigDecimal totalRevenue;

    @Column(name = "avg_price_per_night", updatable = false, insertable = false)
    private Double avgPricePerNight;
}