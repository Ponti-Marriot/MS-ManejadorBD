package com.pontimarriot.manejadorDB.controller;

import com.pontimarriot.manejadorDB.dtos.*;
import com.pontimarriot.manejadorDB.model.*;
import com.pontimarriot.manejadorDB.repository.HotelPropertyRepository;
import com.pontimarriot.manejadorDB.repository.LocationRepository;
import com.pontimarriot.manejadorDB.service.RoomsService;
import com.pontimarriot.manejadorDB.service.PaymentsService;
import com.pontimarriot.manejadorDB.service.ReservationsService;
import com.pontimarriot.manejadorDB.service.SettingsService;
import com.pontimarriot.manejadorDB.service.ReportsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*") // permite llamadas desde http://localhost:4200
public class FrontController {

    private final RoomsService roomsService;
    private final PaymentsService paymentsService;
    private final ReservationsService reservationsService;
    private final SettingsService settingsService;
    private final ReportsService reportsService;
    private final HotelPropertyRepository hotelPropertyRepository;
    private final LocationRepository locationRepository;

    public FrontController(RoomsService roomsService,
                           PaymentsService paymentsService,
                           ReservationsService reservationsService,
                           SettingsService settingsService,
                           ReportsService reportsService,
                           HotelPropertyRepository hotelPropertyRepository,
                           LocationRepository locationRepository) {
        this.roomsService = roomsService;
        this.paymentsService = paymentsService;
        this.reservationsService = reservationsService;
        this.settingsService = settingsService;
        this.reportsService = reportsService;
        this.hotelPropertyRepository = hotelPropertyRepository;
        this.locationRepository = locationRepository;
    }

    // =========================================================
    //                       LOCATIONS
    // =========================================================

    // GET /api/locations -> lista de regiones / ciudades
    @GetMapping("/locations")
    public List<LocationSimpleDTO> getAllLocations() {
        return roomsService.getAllLocationsSimple();
    }

    // GET /api/locations/{locationId}
    @GetMapping("/locations/{locationId}")
    public Location getLocationById(@PathVariable UUID locationId) {
        return locationRepository.findById(locationId).orElse(null);
    }

    // GET /api/locations/{locationId}/hotels -> hoteles en esa location
    @GetMapping("/locations/{locationId}/hotels")
    public List<HotelProperty> getHotelsByLocation(@PathVariable UUID locationId) {
        // Asegúrate de tener en HotelPropertyRepository:
        // List<HotelProperty> findByLocationId(UUID locationId);
        return hotelPropertyRepository.findByLocationId(locationId);
    }

    // =========================================================
    //                       HOTELS
    // =========================================================

    @GetMapping("/hotels")
    public List<HotelProperty> getAllHotels() {
        return roomsService.getAllHotelProperties();
    }

    @GetMapping("/hotels/{hotelPropertyId}")
    public HotelProperty getHotelProperty(@PathVariable UUID hotelPropertyId) {
        return roomsService.getHotelPropertyById(hotelPropertyId);
    }

    // =========================================================
    //                       ROOMS
    // =========================================================

    // Todas las rooms (DTO)
    @GetMapping("/rooms")
    public List<RoomResponseDTO> getAllRooms() {
        return roomsService.getAllRooms();
    }

    // Rooms resumen por hotel (para tu pantalla de settings)
    @GetMapping("/rooms/hotel/{hotelId}")
    public List<RoomResponseDTO> getRoomsByHotel(@PathVariable UUID hotelId) {
        return roomsService.getRoomsByHotelPropertyDetailed(hotelId);
    }

    // Rooms entidad Room por hotel (si lo usas en otro lado)
    @GetMapping("/hotels/{hotelPropertyId}/rooms")
    public List<Room> getRoomsByHotelProperty(@PathVariable UUID hotelPropertyId) {
        return roomsService.getRoomsByHotelProperty(hotelPropertyId);
    }

    @PostMapping("/rooms/hotel/{hotelId}")
    public RoomResponseDTO createRoomForHotel(
            @PathVariable UUID hotelId,
            @RequestBody CreateRoomRequestDTO body
    ) {
        return roomsService.createRoomForHotel(
                hotelId,
                body.getTitle(),
                body.getDescription(),
                body.getRoomType(),
                body.getPricePerNight(),
                body.getBedrooms(),
                body.getBathrooms(),
                body.getRoomServiceIds()
        );
    }

    @PutMapping("/rooms/{roomId}")
    public RoomResponseDTO updateRoomForHotel(
            @PathVariable UUID roomId,
            @RequestBody CreateRoomRequestDTO body
    ) {
        return roomsService.updateRoomForHotel(
                roomId,
                body.getHotelPropertyId(),
                body.getTitle(),
                body.getDescription(),
                body.getRoomType(),
                body.getPricePerNight(),
                body.getBedrooms(),
                body.getBathrooms(),
                body.getRoomServiceIds()
        );
    }

    @DeleteMapping("/rooms/{roomId}")
    public void deleteRoom(@PathVariable UUID roomId) {
        roomsService.deleteRoomCompletely(roomId);
    }

    @GetMapping("/rooms/{roomId}/services")
    public List<Service> getServicesByRoom(@PathVariable UUID roomId) {
        return roomsService.getServicesByRoom(roomId);
    }

    @GetMapping("/rooms/{roomId}/availability")
    public List<AvailabilityDates> getAvailabilityByRoom(@PathVariable UUID roomId) {
        return roomsService.getAvailabilityByRoom(roomId);
    }

    // =========================================================
    //                       PAYMENTS
    // =========================================================

    @GetMapping("/payments")
    public List<Payment> getAllPayments() {
        return paymentsService.getAllPayments();
    }

    @GetMapping("/payments/{paymentId}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable UUID paymentId) {
        return paymentsService.getPaymentById(paymentId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/reservations/{reservationId}/payments")
    public List<Payment> getPaymentsByReservation(@PathVariable UUID reservationId) {
        return paymentsService.getPaymentsByReservation(reservationId);
    }

    // =========================================================
    //                       RESERVATIONS
    // =========================================================

    @GetMapping("/reservations")
    public List<ReservationSummaryDTO> getAllReservations() {
        return reservationsService.getAllReservations();
    }

    @GetMapping("/reservations/{reservationId}")
    public ReservationDetailsDTO getReservationById(@PathVariable UUID reservationId) {
        return reservationsService.getReservationDetails(reservationId);
    }

    @GetMapping("/rooms/{roomId}/reservations")
    public List<ReservationSummaryDTO> getReservationsByRoom(@PathVariable UUID roomId) {
        return reservationsService.getReservationsByRoom(roomId);
    }

    @GetMapping("/hotels/{hotelPropertyId}/reservations")
    public List<ReservationSummaryDTO> getReservationsByHotel(@PathVariable UUID hotelPropertyId) {
        return reservationsService.getReservationsByHotelProperty(hotelPropertyId);
    }

    // =========================================================
    //                       SETTINGS
    // =========================================================

    @GetMapping("/settings")
    public List<Setting> getAllSettings() {
        return settingsService.getAllSettings();
    }

    @GetMapping("/settings/{settingId}")
    public Setting getSettingById(@PathVariable UUID settingId) {
        return settingsService.getSettingById(settingId);
    }

    @GetMapping("/settings/key/{key}")
    public Setting getSettingByKey(@PathVariable String key) {
        return settingsService.getSettingByKey(key);
    }

    // =========================================================
    //                       REPORTS
    // =========================================================

    @GetMapping("/reports")
    public List<Report> getAllReports() {
        return reportsService.getAllReports();
    }

    @GetMapping("/reports/{reportId}")
    public Report getReportById(@PathVariable UUID reportId) {
        return reportsService.getReportById(reportId);
    }

    @GetMapping("/reports/range")
    public List<Report> getReportsInRange(@RequestParam("start") String start,
                                          @RequestParam("end") String end) {
        LocalDate startDate = LocalDate.parse(start);
        LocalDate endDate = LocalDate.parse(end);
        return reportsService.getReportsBetween(startDate, endDate);
    }
}
