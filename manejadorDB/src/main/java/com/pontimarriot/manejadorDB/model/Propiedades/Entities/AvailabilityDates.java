package com.pontimarriot.manejadorDB.model.Propiedades.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
public class AvailabilityDates {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private String createdAt;

    public AvailabilityDates() {
    }

    public AvailabilityDates(UUID roomId, LocalDate startDate, LocalDate endDate, String createdAt) {
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
    }
}