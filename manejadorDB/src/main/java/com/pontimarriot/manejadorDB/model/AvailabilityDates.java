package com.pontimarriot.manejadorDB.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "availabilitydates")
public class AvailabilityDates {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Column(name = "hotelpropertiesrooms_id")
    private UUID hotelpropertiesroomsId;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "finish_date")
    private LocalDate finishDate;
    @Column(name = "created_at")
    private String createdAt;

    public AvailabilityDates() {
    }

    public AvailabilityDates(UUID hotelpropertiesroomsId, LocalDate startDate, LocalDate finishDate, String createdAt) {
        this.hotelpropertiesroomsId = hotelpropertiesroomsId;
        this.startDate = startDate;
        this.finishDate = finishDate;
        this.createdAt = createdAt;
    }
}