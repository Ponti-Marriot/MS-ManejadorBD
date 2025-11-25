package com.pontimarriot.manejadorDB.repository;

import com.pontimarriot.manejadorDB.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public interface ReportRepository extends JpaRepository<Report, UUID> {

    List<Report> findByCreatedAtBetween(LocalDate start, LocalDate end);
}
