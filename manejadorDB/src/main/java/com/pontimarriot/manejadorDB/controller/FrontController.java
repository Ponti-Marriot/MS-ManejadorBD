package com.pontimarriot.manejadorDB.controller;

import com.pontimarriot.manejadorDB.model.*;
import com.pontimarriot.manejadorDB.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // Adjust allowed origins for your frontend
public class FrontController {

    private final RoomsService roomsService;
    private final PaymentsService paymentsService;
    private final ReservationsService reservationsService;
    private final SettingsService settingsService;
    private final ReportsService reportsService;

    public FrontController(RoomsService roomsService,
                           PaymentsService paymentsService,
                           ReservationsService reservationsService,
                           SettingsService settingsService,
                           ReportsService reportsService) {
        this.roomsService = roomsService;
        this.paymentsService = paymentsService;
        this.reservationsService = reservationsService;
        this.settingsService = settingsService;
        this.reportsService = reportsService;
    }

    // ==================== ROOMS ====================

    // GET /api/rooms
    @GetMapping("/rooms")
    public List<Room> getAllRooms() {
        return roomsService.getAllRooms();
    }

    // GET /api/rooms/{roomId}
    @GetMapping("/rooms/{roomId}")
    public Room getRoomById(@PathVariable UUID roomId) {
        return roomsService.getRoomById(roomId);
    }

    // GET /api/hotels/{hotelPropertyId}/rooms
    @GetMapping("/hotels/{hotelPropertyId}/rooms")
    public List<Room> getRoomsByHotelProperty(@PathVariable UUID hotelPropertyId) {
        return roomsService.getRoomsByHotelProperty(hotelPropertyId);
    }

    // GET /api/rooms/{roomId}/services
    @GetMapping("/rooms/{roomId}/services")
    public List<Service> getServicesByRoom(@PathVariable UUID roomId) {
        return roomsService.getServicesByRoom(roomId);
    }

    // GET /api/rooms/{roomId}/availability
    @GetMapping("/rooms/{roomId}/availability")
    public List<AvailabilityDates> getAvailabilityByRoom(@PathVariable UUID roomId) {
        return roomsService.getAvailabilityByRoom(roomId);
    }

    // GET /api/hotels
    @GetMapping("/hotels")
    public List<HotelProperty> getAllHotelProperties() {
        return roomsService.getAllHotelProperties();
    }

    // GET /api/hotels/{hotelPropertyId}
    @GetMapping("/hotels/{hotelPropertyId}")
    public HotelProperty getHotelProperty(@PathVariable UUID hotelPropertyId) {
        return roomsService.getHotelPropertyById(hotelPropertyId);
    }

    // ==================== PAYMENTS ====================

    // GET /api/payments
    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return paymentsService.getAllPayments();
    }

    // GET /api/payments/{paymentId}
    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID paymentId) {
        return paymentsService.getPaymentById(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // GET /api/reservations/{reservationId}/payments
    @GetMapping("/reservations/{reservationId}/payments")
    public List<Payment> getPaymentsByReservation(@PathVariable UUID reservationId) {
        return paymentsService.getPaymentsByReservation(reservationId);
    }

    // ==================== RESERVATIONS ====================

    // GET /api/reservations
    @GetMapping("/reservations")
    public List<Reserva> getAllReservations() {
        return reservationsService.getAllReservations();
    }

    // GET /api/reservations/{reservationId}
    @GetMapping("/reservations/{reservationId}")
    public Reserva getReservationById(@PathVariable UUID reservationId) {
        return reservationsService.getReservationById(reservationId);
    }

    // GET /api/rooms/{roomId}/reservations
    @GetMapping("/rooms/{roomId}/reservations")
    public List<Reserva> getReservationsByRoom(@PathVariable UUID roomId) {
        return reservationsService.getReservationsByRoom(roomId);
    }

    // GET /api/hotels/{hotelPropertyId}/reservations
    @GetMapping("/hotels/{hotelPropertyId}/reservations")
    public List<Reserva> getReservationsByHotel(@PathVariable UUID hotelPropertyId) {
        return reservationsService.getReservationsByHotelProperty(hotelPropertyId);
    }

    // ==================== SETTINGS ====================

    // GET /api/settings
    @GetMapping("/settings")
    public List<Setting> getAllSettings() {
        return settingsService.getAllSettings();
    }

    // GET /api/settings/{settingId}
    @GetMapping("/settings/{settingId}")
    public Setting getSettingById(@PathVariable UUID settingId) {
        return settingsService.getSettingById(settingId);
    }

    // GET /api/settings/key/{key}
    @GetMapping("/settings/key/{key}")
    public Setting getSettingByKey(@PathVariable String key) {
        return settingsService.getSettingByKey(key);
    }

    // ==================== REPORTS ====================

    // GET /api/reports
    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return reportsService.getAllReports();
    }

    // GET /api/reports/{reportId}
    @GetMapping("/reports/{reportId}")
    public Report getReportById(@PathVariable UUID reportId) {
        return reportsService.getReportById(reportId);
    }

    // GET /api/reports/range?start=2025-11-01&end=2025-11-30
    @GetMapping("/reports/range")
    public List<Report> getReportsInRange(@RequestParam("start") String start,
                                          @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return reportsService.getReportsBetween(startDate, endDate);
    }
}