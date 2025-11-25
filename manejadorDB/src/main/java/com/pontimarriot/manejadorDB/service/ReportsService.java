package com.pontimarriot.manejadorDB.service;


import com.pontimarriot.manejadorDB.model.Report;
import com.pontimarriot.manejadorDB.repository.ReportRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class ReportsService {

    private final ReportRepository reportRepository;

    public ReportsService(ReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    // Get all reports
    public List<Report> getAllReports() {
        return reportRepository.findAll();
    }

    // Get one report
    public Report getReportById(UUID reportId) {
        return reportRepository.findById(reportId).orElse(null);
    }

    // Example: reports by date range
    public List<Report> getReportsBetween(LocalDate start, LocalDate end) {
        return reportRepository.findByCreatedAtBetween(start, end);
    }
}
